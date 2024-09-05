// join.js
document.addEventListener('DOMContentLoaded', function() {
    var profileImageInput = document.getElementById('profileImage');
    var imagePreview = document.getElementById('imagePreview');
    var imageLoaded = false; // 이미지가 로드되었는지 여부를 추적하는 변수

    profileImageInput.addEventListener('change', function(event) {
        if (imageLoaded) return; // 이미지가 이미 로드된 경우, 더 이상 실행하지 않음

        var files = event.target.files;

        // Clear any existing previews
        imagePreview.innerHTML = '';

        if (files && files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                var img = document.createElement('img');
                img.src = e.target.result;
                img.style.maxWidth = '100%'; // 스타일 설정: 필요에 따라 조정
                img.style.maxHeight = '300px'; // 스타일 설정: 필요에 따라 조정
                imagePreview.appendChild(img);

                // 이미지가 로드되었음을 표시
                imageLoaded = true;
            };

            reader.readAsDataURL(files[0]);
        }
    });
});
