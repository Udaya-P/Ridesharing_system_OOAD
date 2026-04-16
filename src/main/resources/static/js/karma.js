const karmaForm = document.getElementById("karma-form");
const karmaMessage = document.getElementById("karma-message");
const karmaResultCard = document.getElementById("karma-result-card");
const karmaScoreValue = document.getElementById("karma-score-value");
const karmaScoreCaption = document.getElementById("karma-score-caption");

if (karmaForm && karmaMessage && karmaResultCard && karmaScoreValue && karmaScoreCaption) {
    karmaForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const formData = new FormData(karmaForm);
        const userId = String(formData.get("userId") || "").trim();

        if (!userId) {
            setKarmaMessage("User ID is required.", "error");
            karmaResultCard.classList.add("hidden");
            return;
        }

        setKarmaMessage("Calculating karma...", "pending");

        try {
            const response = await fetch(`/api/reviews/user/${encodeURIComponent(userId)}/karma`);
            const rawBody = await response.text();
            const data = rawBody ? JSON.parse(rawBody) : {};

            if (!response.ok) {
                throw new Error(data.message || `Request failed with status ${response.status}`);
            }

            const karmaScore = Number(data.karmaScore ?? 0);
            karmaScoreValue.textContent = karmaScore.toFixed(2);
            karmaScoreCaption.textContent = `Current karma score for user ${userId}`;
            karmaResultCard.classList.remove("hidden");
            setKarmaMessage("Karma score calculated successfully.", "success");
        } catch (error) {
            karmaResultCard.classList.add("hidden");
            setKarmaMessage(error.message || "Unable to calculate karma.", "error");
        }
    });
}

function setKarmaMessage(message, type) {
    karmaMessage.textContent = message;
    karmaMessage.className = `status-message ${type}`;
}
