const BASE_URL = "http://localhost:8080";

// ADD TASK
document.getElementById("taskForm")?.addEventListener("submit", function(e) {
    e.preventDefault();

    const task = {
        title: document.getElementById("title").value,
        email: document.getElementById("email").value,
        dueDate: document.getElementById("dueDate").value
    };

    fetch(BASE_URL + "/tasks", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(task)
    })
    .then(res => res.json())
    .then(() => {
        document.getElementById("message").innerText =
            "✅ Task Added Successfully!";
    })
    .catch(() => alert("Error adding task"));
});


// LOAD TASKS
function loadTasks() {
    fetch(BASE_URL + "/tasks")
        .then(res => res.json())
        .then(data => {
            let rows = "";
            data.forEach(task => {
                rows += `
                    <tr>
                        <td>${task.id}</td>
                        <td>${task.title}</td>
                        <td>${task.email}</td>
                        <td>${task.dueDate}</td>
                    </tr>`;
            });
            document.getElementById("taskTable").innerHTML = rows;
        });
}


// EXPORT CSV
function exportCSV() {
    window.location.href = BASE_URL + "/reports/export";
}