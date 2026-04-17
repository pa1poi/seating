const API = "http://localhost:8081/api";

function login() {
    let u = document.getElementById("user").value;
    let p = document.getElementById("pass").value;

    if (u === "admin" && p === "admin123") {
        window.location = "upload.html";
    } else {
        alert("Invalid Login");
    }
}

function logout() {
    window.location = "login.html";
}

function uploadStudents() {
    uploadFile("students", "/upload/students");
}

function uploadRooms() {
    uploadFile("rooms", "/upload/rooms");
}

function uploadExam() {
    uploadFile("exam", "/upload/exam");
}

function uploadTeachers() {
    uploadFile("teachers", "/upload/teachers");
}

function uploadFile(id, url) {

    let file = document.getElementById(id).files[0];
    let form = new FormData();
    form.append("file", file);

    fetch(API + url, {
        method: "POST",
        body: form
    })
    .then(r => r.text())
    .then(alert);
}

function generate() {
    fetch(API + "/allocate")
    .then(r => r.text())
    .then(alert);
}

function downloadStudent() {
    window.open(API + "/pdf/student");
}

function downloadTeacher() {
    window.open(API + "/pdf/teacher");
}

function downloadAllocation() {
    window.open(API + "/pdf/teacher-allocation");
}

function sendStudent() {

    let msg = document.getElementById("studentMsg").value;

    fetch(API + "/send/student-mails?msg=" + encodeURIComponent(msg))
    .then(r => r.text())
    .then(alert);
}

function sendTeacher() {

    let msg = document.getElementById("teacherMsg").value;

    fetch(API + "/send/teacher-mails?msg=" + encodeURIComponent(msg))
    .then(r => r.text())
    .then(alert);
}
