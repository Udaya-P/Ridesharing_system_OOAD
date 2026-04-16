const poolForm = document.getElementById("pool-form");
const poolMessage = document.getElementById("pool-message");

if (poolForm && poolMessage) {
    poolForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const formData = new FormData(poolForm);
        const payload = {
            driver: {
                userId: formData.get("driverId")
            },
            poolers: [],
            origin: {
                address: formData.get("originAddress"),
                latitude: Number(formData.get("originLatitude")),
                longitude: Number(formData.get("originLongitude"))
            },
            destination: {
                address: formData.get("destinationAddress"),
                latitude: Number(formData.get("destinationLatitude")),
                longitude: Number(formData.get("destinationLongitude"))
            }
        };

        setPoolMessage("Creating pool...", "pending");

        try {
            const response = await fetch("/api/pools", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            const rawBody = await response.text();
            const data = rawBody ? JSON.parse(rawBody) : null;

            if (!response.ok) {
                const errorMessage = data?.message || `Request failed with status ${response.status}`;
                throw new Error(errorMessage);
            }

            poolForm.reset();
            setPoolMessage(`Pool created successfully. Pool ID: ${data.poolId}`, "success");
        } catch (error) {
            setPoolMessage(error.message || "Unable to create pool.", "error");
        }
    });
}

function setPoolMessage(message, type) {
    poolMessage.textContent = message;
    poolMessage.className = `status-message ${type}`;
}
