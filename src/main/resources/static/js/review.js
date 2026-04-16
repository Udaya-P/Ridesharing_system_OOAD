const reviewForm = document.getElementById("review-form");
const reviewMessage = document.getElementById("review-message");
const reviewContext = document.getElementById("review-context");

const reviewParams = new URLSearchParams(window.location.search);
const reviewPoolId = reviewParams.get("poolId");

if (reviewContext && reviewPoolId) {
    reviewContext.textContent = `Adding review for pool: ${reviewPoolId}`;
    reviewContext.classList.remove("hidden");
}

if (reviewForm && reviewMessage) {
    reviewForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const formData = new FormData(reviewForm);
        const payload = {
            reviewerId: formData.get("reviewerId"),
            revieweeId: formData.get("revieweeId"),
            stars: Number(formData.get("stars")),
            comment: formData.get("comment")
        };

        setReviewMessage("Submitting review...", "pending");

        try {
            const response = await fetch("/api/reviews", {
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

            reviewForm.reset();
            setReviewMessage(`Review submitted successfully. Review ID: ${data.reviewId}`, "success");
        } catch (error) {
            setReviewMessage(error.message || "Unable to submit review.", "error");
        }
    });
}

function setReviewMessage(message, type) {
    reviewMessage.textContent = message;
    reviewMessage.className = `status-message ${type}`;
}
