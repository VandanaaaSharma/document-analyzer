function uploadFile() {
    const file = document.getElementById("fileInput").files[0];

    if (!file) {
        alert("Please select a file");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    document.getElementById("loading").style.display = "block";
    document.getElementById("result").style.display = "none";

    fetch("http://localhost:8080/api/v1/document/analyze", {
        method: "POST",
        body: formData
    })
        .then(res => res.json())
        .then(data => {
            document.getElementById("loading").style.display = "none";
            document.getElementById("result").style.display = "block";

            document.getElementById("summary").textContent = data.summary;
            document.getElementById("keywords").textContent = data.keywords.join(", ");
            document.getElementById("sentiment").textContent = data.sentiment;
        })
        .catch(err => {
            alert("Error analyzing document");
            console.error(err);
        });
}

