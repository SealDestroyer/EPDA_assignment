// Form validation functions for Add Class Student form

// Main validation function
function validateForm() {
    let isValid = true;
    
    isValid = validateStudentID() && isValid;
    isValid = validateEnrollmentDate() && isValid;
    
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

// Validate Enrollment Date
function validateEnrollmentDate() {
    const enrollmentDate = document.getElementById('enrollmentDate');
    const errorRow = document.getElementById('enrollmentDate-error');
    const errorMessage = document.getElementById('enrollmentDate-error-message');
    
    if (!enrollmentDate.value) {
        showError(errorRow, errorMessage, 'Enrollment date is required', enrollmentDate);
        return false;
    }
    
    // Check if date is valid
    const selectedDate = new Date(enrollmentDate.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    if (isNaN(selectedDate.getTime())) {
        showError(errorRow, errorMessage, 'Please enter a valid date', enrollmentDate);
        return false;
    }
    
    // Check if date is not too far in the past (e.g., more than 1 month ago)
    const oneMonthAgo = new Date();
    oneMonthAgo.setMonth(oneMonthAgo.getMonth() - 1);
    oneMonthAgo.setHours(0, 0, 0, 0);
    
    if (selectedDate < oneMonthAgo) {
        showError(errorRow, errorMessage, 'Enrollment date cannot be more than 1 month ago', enrollmentDate);
        return false;
    }
    
    hideError(errorRow, enrollmentDate);
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
