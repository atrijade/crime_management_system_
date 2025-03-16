document.addEventListener("DOMContentLoaded", function () {
    let cases = document.getElementById("totalCases");
    let criminals = document.getElementById("totalCriminals");
    let officers = document.getElementById("totalOfficers");
    let judges = document.getElementById("totalJudges");

    // Simulated dynamic data (can be replaced with API calls)
    let caseCount = 50;
    let criminalCount = 30;
    let officerCount = 10;
    let judgeCount = 5;

    function updateDashboard() {
        cases.textContent = caseCount;
        criminals.textContent = criminalCount;
        officers.textContent = officerCount;
        judges.textContent = judgeCount;
    }

    updateDashboard();
});
