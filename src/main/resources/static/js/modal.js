// 모달 열기
function openModal(obj) {

    writerId = obj.dataset.id;
    console.log(obj.dataset.id);
    console.log("신고글 작성자 : ", writerId      );
    const modal = document.getElementById("reportModal");
    const reportedGuestIdInput = document.getElementById("reportedGuestId");
    reportedGuestIdInput.value = writerId;  //신고당하는  방명록 글작성자 ID 설정
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
