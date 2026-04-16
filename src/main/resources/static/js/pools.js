const poolsList = document.getElementById("pools-list");
const poolsMessage = document.getElementById("pools-message");
const refreshPoolsButton = document.getElementById("refresh-pools");
const joinPoolerIdInput = document.getElementById("join-pooler-id");
const joinSuccessToast = document.getElementById("join-success-toast");
let toastTimeoutId;

if (poolsList && poolsMessage) {
    loadPools();
}

if (refreshPoolsButton) {
    refreshPoolsButton.addEventListener("click", loadPools);
}

async function loadPools() {
    poolsMessage.textContent = "Loading pools...";
    poolsMessage.className = "status-message pending";

    try {
        const response = await fetch("/api/pools");
        const pools = await response.json();

        if (!response.ok) {
            throw new Error(`Request failed with status ${response.status}`);
        }

        renderPools(Array.isArray(pools) ? pools : []);
    } catch (error) {
        poolsList.innerHTML = "";
        poolsMessage.textContent = error.message || "Unable to load pools.";
        poolsMessage.className = "status-message error";
    }
}

function renderPools(pools) {
    if (!pools.length) {
        poolsList.innerHTML = "";
        poolsMessage.textContent = "No pools available right now.";
        poolsMessage.className = "status-message pending";
        return;
    }

    poolsMessage.textContent = `${pools.length} pool(s) loaded`;
    poolsMessage.className = "status-message success";

    poolsList.innerHTML = pools.map((pool) => `
        <article class="pool-item-card">
            <div class="pool-item-top">
                <div>
                    <p class="pool-item-label">Driver</p>
                    <h3>${escapeHtml(pool.driver?.name || "Unknown Driver")}</h3>
                </div>
                <span class="pool-status-badge ${getStatusClass(pool.status)}">${escapeHtml(pool.status || "UNKNOWN")}</span>
            </div>
            <div class="pool-item-grid">
                <div>
                    <p class="pool-item-label">Origin</p>
                    <p>${escapeHtml(pool.origin?.address || "-")}</p>
                </div>
                <div>
                    <p class="pool-item-label">Destination</p>
                    <p>${escapeHtml(pool.destination?.address || "-")}</p>
                </div>
                <div>
                    <p class="pool-item-label">Capacity</p>
                    <p>${pool.capacity ?? "-"}</p>
                </div>
                <div>
                    <p class="pool-item-label">Current Poolers</p>
                    <p>${pool.currentPoolerCount ?? 0}</p>
                </div>
                <div class="full-width">
                    <p class="pool-item-label">Pool ID</p>
                    <p class="pool-id">${escapeHtml(pool.poolId || "-")}</p>
                </div>
            </div>
            <div class="ride-status-panel" data-ride-panel="${escapeHtml(pool.poolId || "")}"></div>
            <div class="pool-action-row">
                <button type="button" class="sky-button join-pool-button" data-pool-id="${escapeHtml(pool.poolId || "")}">
                    Join Pool
                </button>
                ${pool.status === "FULL" ? `
                    <button type="button" class="amber-button pool-action-button start-pool-button" data-pool-id="${escapeHtml(pool.poolId || "")}">
                        Start Pool
                    </button>
                ` : ""}
                ${pool.status === "IN_PROGRESS" ? `
                    <button type="button" class="violet-button pool-action-button complete-pool-button" data-pool-id="${escapeHtml(pool.poolId || "")}">
                        Complete Pool
                    </button>
                ` : ""}
            </div>
        </article>
    `).join("");

    attachJoinHandlers();
    attachLifecycleHandlers();
    loadRideStatuses(pools);
}

function attachJoinHandlers() {
    const joinButtons = poolsList.querySelectorAll(".join-pool-button");
    joinButtons.forEach((button) => {
        button.addEventListener("click", async () => {
            await joinPool(button.dataset.poolId);
        });
    });
}

function attachLifecycleHandlers() {
    const startButtons = poolsList.querySelectorAll(".start-pool-button");
    startButtons.forEach((button) => {
        button.addEventListener("click", async () => {
            await updatePoolLifecycle(button.dataset.poolId, "start");
        });
    });

    const completeButtons = poolsList.querySelectorAll(".complete-pool-button");
    completeButtons.forEach((button) => {
        button.addEventListener("click", async () => {
            await updatePoolLifecycle(button.dataset.poolId, "complete");
        });
    });
}

async function joinPool(poolId) {
    const poolerId = joinPoolerIdInput?.value?.trim();

    if (!poolerId) {
        poolsMessage.textContent = "Pooler ID is required to join a pool.";
        poolsMessage.className = "status-message error";
        return;
    }

    try {
        const response = await fetch("/api/pools/join", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                poolId,
                poolerId
            })
        });

        const result = await response.json().catch(() => ({}));

        if (!response.ok) {
            throw new Error(result.message || `Join failed with status ${response.status}`);
        }

        poolsMessage.textContent = "Successfully joined the pool.";
        poolsMessage.className = "status-message success";
        showToast("Pooler successfully joined.");
        await loadPools();
    } catch (error) {
        poolsMessage.textContent = error.message || "Unable to join pool.";
        poolsMessage.className = "status-message error";
    }
}

async function updatePoolLifecycle(poolId, action) {
    const actionLabel = action === "start" ? "Starting pool..." : "Completing pool...";
    const successLabel = action === "start" ? "Pool started successfully." : "Pool completed successfully.";

    poolsMessage.textContent = actionLabel;
    poolsMessage.className = "status-message pending";

    try {
        const response = await fetch(`/api/pools/${encodeURIComponent(poolId)}/${action}`, {
            method: "POST"
        });

        const result = await response.json().catch(() => ({}));

        if (!response.ok) {
            throw new Error(result.message || `${action} failed with status ${response.status}`);
        }

        poolsMessage.textContent = successLabel;
        poolsMessage.className = "status-message success";
        await loadPools();
    } catch (error) {
        poolsMessage.textContent = error.message || `Unable to ${action} pool.`;
        poolsMessage.className = "status-message error";
    }
}

function getStatusClass(status) {
    switch (status) {
        case "OPEN":
            return "status-open";
        case "FULL":
            return "status-full";
        case "IN_PROGRESS":
            return "status-progress";
        case "COMPLETED":
            return "status-completed";
        default:
            return "status-default";
    }
}

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll("\"", "&quot;")
        .replaceAll("'", "&#39;");
}

function showToast(message) {
    if (!joinSuccessToast) {
        return;
    }

    joinSuccessToast.textContent = message;
    joinSuccessToast.classList.add("visible");
    joinSuccessToast.setAttribute("aria-hidden", "false");

    if (toastTimeoutId) {
        window.clearTimeout(toastTimeoutId);
    }

    toastTimeoutId = window.setTimeout(() => {
        joinSuccessToast.classList.remove("visible");
        joinSuccessToast.setAttribute("aria-hidden", "true");
    }, 2600);
}

async function loadRideStatuses(pools) {
    await Promise.all(pools.map(async (pool) => {
        if (!pool.poolId) {
            return;
        }

        const panel = document.querySelector(`[data-ride-panel="${escapeHtml(pool.poolId)}"]`);
        if (!panel) {
            return;
        }

        try {
            const response = await fetch(`/rides/pool/${encodeURIComponent(pool.poolId)}`);
            const data = await response.json().catch(() => ({}));

            if (!response.ok) {
                panel.innerHTML = "";
                return;
            }

            if (data.status === "COMPLETED") {
                panel.innerHTML = `
                    <div class="ride-review-card">
                        <p class="ride-review-message">Ride is finished. Would you like to add your review?</p>
                        <button type="button" class="emerald-button ride-review-button">
                            Add Review
                        </button>
                    </div>
                `;

                panel.querySelector(".ride-review-button")?.addEventListener("click", () => {
                    window.location.href = `/add-review?poolId=${encodeURIComponent(pool.poolId)}`;
                });
                return;
            }

            if (data.status === "IN_PROGRESS") {
                panel.innerHTML = `<p class="ride-status-message">Ride is currently in progress.</p>`;
                return;
            }

            panel.innerHTML = "";
        } catch (error) {
            panel.innerHTML = "";
        }
    }));
}
