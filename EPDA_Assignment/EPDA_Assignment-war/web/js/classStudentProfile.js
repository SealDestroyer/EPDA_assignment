// Form validation functions for Add Class Student form

// Main validation function
function validateForm() {
    let isValid = true;
    
    isValid = validateStudentID() && isValid;
    
    return isValid;
}

// Validate Student ID
function validateStudentID() {
    const studentID = document.getElementById('studentID');
    const errorRow = document.getElementById('studentID-error');
    const errorMessage = document.getElementById('studentID-error-message');
    
    if (!studentID.value || studentID.value === '') {
        showError(errorRow, errorMessage, 'Please select a student', studentID);
        return false;
    }
    
    hideError(errorRow, studentID);
    return true;
}

// Helper function to show error
function showError(errorRow, errorMessage, message, inputElement) {
    errorRow.style.display = 'table-row';
    errorMessage.textContent = message;
    inputElement.classList.add('input-error');
}

// Helper function to hide error
function hideError(errorRow, inputElement) {
    errorRow.style.display = 'none';
    inputElement.classList.remove('input-error');
}
