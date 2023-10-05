async function loadTable(url, table) {
    const tableHead = table.querySelector("thead")
    const tableBody = table.querySelector("tbody")
    const response = await fetch(url);
    const {rows} = response.json();
    tableBody.innerHTML = ""
    for (const row of rows) {
        const rowElement = document.createElement("tr")
        for (const cellText of row) {
            const cellElement = document.createElement("td");
            cellElement.textContent = cellText.value()
            rowElement.appendChild(cellElement)
        }

        tableBody.appendChild(rowElement);
    }
}

loadTable("http://localhost:8080/admin/rest/users/", document.querySelector("table"))