import Swal from 'sweetalert2';

export function showWarningAlert(title, text) {
  Swal.fire({
    title: title,
    text: text,
    icon: 'warning',
    showCancelButton: false,
    confirmButtonColor: '#3085d6',
    confirmButtonText: 'OK',
    showCloseButton: true,
  });
}
