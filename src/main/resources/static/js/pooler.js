const poolerForm = document.getElementById("pooler-form");
const poolerMessage = document.getElementById("pooler-message");

if (poolerForm && poolerMessage) {
    poolerForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const formData = new FormData(poolerForm);
        const payload = {
            name: formData.get("name"),
            email: formData.get("email"),
            phone: formData.get("phone"),
            paymentMethod: formData.get("paymentMethod"),
            currentLocation: {
                address: formData.get("currentAddress"),
                latitude: Number(formData.get("currentLatitude")),
                longitude: Number(formData.get("currentLongitude"))
            },
            destination: {
                address: formData.get("destinationAddress"),
                latitude: Number(formData.get("destinationLatitude")),
                longitude: Number(formData.get("destinationLongitude"))
            }
        };

        setPoolerMessage("Submitting pooler profile...", "pending");

        try {
            const response = await fetch("/api/auth/poolers/register", {
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

            poolerForm.reset();
            setPoolerMessage(`Pooler registered successfully. User ID: ${data.userId}`, "success");
        } catch (error) {
            setPoolerMessage(error.message || "Unable to register pooler.", "error");
        }
    });
}

function setPoolerMessage(message, type) {
    poolerMessage.textContent = message;
    poolerMessage.className = `status-message ${type}`;
}
