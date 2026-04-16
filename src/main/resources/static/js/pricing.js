const pricingForm = document.getElementById("pricing-form");
const pricingMessage = document.getElementById("pricing-message");
const pricingResults = document.getElementById("pricing-results");
const totalFareNode = document.getElementById("total-fare");
const priceBreakdownNode = document.getElementById("price-breakdown");
const fareSplitNode = document.getElementById("fare-split");

if (pricingForm && pricingMessage) {
    pricingForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const formData = new FormData(pricingForm);
        const poolId = String(formData.get("poolId") || "").trim();

        if (!poolId) {
            setPricingMessage("Pool ID is required.", "error");
            return;
        }

        setPricingMessage("Loading pricing...", "pending");
        pricingResults.hidden = true;

        try {
            const [fareResponse, breakdownResponse, splitResponse] = await Promise.all([
                fetch(`/api/pricing/${encodeURIComponent(poolId)}/fare`),
                fetch(`/api/pricing/${encodeURIComponent(poolId)}/breakdown`),
                fetch(`/api/pricing/${encodeURIComponent(poolId)}/split`)
            ]);

            const farePayload = await parseJson(fareResponse);
            const breakdownPayload = await parseJson(breakdownResponse);
            const splitPayload = await parseJson(splitResponse);

            const firstError = [
                { response: fareResponse, payload: farePayload },
                { response: breakdownResponse, payload: breakdownPayload },
                { response: splitResponse, payload: splitPayload }
            ].find(({ response }) => !response.ok);

            if (firstError) {
                throw new Error(firstError.payload?.message || `Request failed with status ${firstError.response.status}`);
            }

            totalFareNode.textContent = formatCurrency(farePayload.totalFare);
            priceBreakdownNode.textContent = JSON.stringify(breakdownPayload, null, 2);
            fareSplitNode.textContent = JSON.stringify(splitPayload, null, 2);

            pricingResults.hidden = false;
            setPricingMessage("Pricing fetched successfully.", "success");
        } catch (error) {
            setPricingMessage(error.message || "Unable to fetch pricing.", "error");
        }
    });
}

function setPricingMessage(message, type) {
    pricingMessage.textContent = message;
    pricingMessage.className = `status-message ${type}`;
}

async function parseJson(response) {
    const rawBody = await response.text();
    return rawBody ? JSON.parse(rawBody) : {};
}

function formatCurrency(value) {
    const numericValue = Number(value);
    if (Number.isNaN(numericValue)) {
        return "-";
    }
    return `₹${numericValue.toFixed(2)}`;
}
