const driverForm = document.getElementById("driver-form");
const driverMessage = document.getElementById("driver-message");

if (driverForm && driverMessage) {
    driverForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const formData = new FormData(driverForm);
        const payload = {
            name: formData.get("name"),
            email: formData.get("email"),
            phone: formData.get("phone"),
            licenseNumber: formData.get("licenseNumber"),
            isAvailable: formData.get("isAvailable") === "on",
            vehicleInfo: {
                make: formData.get("vehicleMake"),
                model: formData.get("vehicleModel"),
                plateNumber: formData.get("plateNumber"),
                seats: Number(formData.get("seats"))
            },
            currentLocation: {
                address: formData.get("address"),
                latitude: Number(formData.get("latitude")),
                longitude: Number(formData.get("longitude"))
            }
        };

        setMessage("Submitting driver profile...", "pending");

        try {
            const response = await fetch("/api/auth/drivers/register", {
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

            driverForm.reset();
            setMessage(`Driver registered successfully. User ID: ${data.userId}`, "success");
        } catch (error) {
            setMessage(error.message || "Unable to register driver.", "error");
        }
    });
}

function setMessage(message, type) {
    driverMessage.textContent = message;
    driverMessage.className = `status-message ${type}`;
}
