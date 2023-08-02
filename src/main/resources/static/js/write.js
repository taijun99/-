window.addEventListener('load', function() {

	const container = document.querySelector('.container');
	const frame = container.querySelector('.frame');
	const fileInput = container.querySelector('input[type="file"]');

	fileInput.addEventListener('input', () => {

		if(!isImage(fileInput.files[0])) {
			alert('Image 파일만 업로드 할 수 있습니다.');
			return;
		}

		const reader = new FileReader();

		reader.addEventListener('load', () => {

			const img = document.createElement('IMG');
			img.classList.add('thumbnail');
			img.src = reader.result;

			frame.insertAdjacentElement('beforeend', img);
		});

		reader.readAsDataURL(fileInput.files[0]);
	});

	function isImage(file){
		return file.type.indexOf('image') >= 0;
	}
});