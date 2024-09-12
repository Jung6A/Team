// 모달 열기
function openModal(guestId) {
    console.log("Received guestId:", guestId);
    const modal = document.getElementById("reportModal");
    const reportedGuestIdInput = document.getElementById("reportedGuestId");
    reportedGuestIdInput.value = guestId;  // 신고할 방명록 ID 설정
    modal.style.display = "block";
};

// 모달 닫기
function closeModal() {
    const modal = document.getElementById("reportModal");
    modal.style.display = "none";
};

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
    const modal = document.getElementById("reportModal");
    if (event.target === modal) {
        modal.style.display = "none";
    };
};
