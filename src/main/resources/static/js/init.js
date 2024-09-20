document.querySelectorAll('.post-content').forEach(function (Repost) {
    if (Repost.textContent.trim() === '이 글은 관리자에 의해 삭제된 게시글 입니다.') {
        Repost.style.color = 'red';
    }
});