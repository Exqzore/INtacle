const uploadPreviewImageForm = document.getElementById('preview_image-form')
const previewImageSelect = document.getElementById('preview_image_select')
const previewImage = document.getElementById('preview_image')
const inputPreviewImagePath = document.getElementById('preview_image_path')

function loadPreviewImage() {
    previewImageSelect.click()
    previewImageSelect.onchange = e => {
        fetch(uploadPreviewImageForm.action, {
            method: uploadPreviewImageForm.method,
            body: new FormData(uploadPreviewImageForm)
        }).then(response => {
            if (response.ok) {
                response.text().then(path => {
                    previewImage.src = 'main?command=take_file&file_name=' + path
                    inputPreviewImagePath.value = path
                })
            }
        })
    }
}