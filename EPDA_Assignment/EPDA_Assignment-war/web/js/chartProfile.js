// Form validation functions for Generate Report forms

// Main validation function
function validateForm() {
    let isValid = true;
    
    isValid = validateStartDate() && isValid;
    isValid = validateEndDate() && isValid;
    
    return isValid;
}

// Validate Start Date
function validateStartDate() {
    const startDate = document.getElementById('startDate');
    const errorRow = document.getElementById('startDate-error');
    const errorMessage = document.getElementById('startDate-error-message');
    
    if (!startDate.value.trim()) {
        showError(errorRow, errorMessage, 'Start date is required', startDate);
        return false;
    }
    
    const start = new Date(startDate.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    if (start > today) {
        showError(errorRow, errorMessage, 'Start date cannot be in the future', startDate);
        return false;
    }
    
    // Also validate against end date if it exists
    const endDate = document.getElementById('endDate').value;
    if (endDate) {
        const end = new Date(endDate);
        if (start > end) {
            showError(errorRow, errorMessage, 'Start date cannot be after end date', startDate);
            return false;
        }
    }
    
    hideError(errorRow, startDate);
    return true;
}

// Validate End Date
function validateEndDate() {
    const endDate = document.getElementById('endDate');
    const errorRow = document.getElementById('endDate-error');
    const errorMessage = document.getElementById('endDate-error-message');
    
    if (!endDate.value.trim()) {
        showError(errorRow, errorMessage, 'End date is required', endDate);
        return false;
    }
    
    const end = new Date(endDate.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    if (end > today) {
        showError(errorRow, errorMessage, 'End date cannot be in the future', endDate);
        return false;
    }
    
    // Also validate against start date if it exists
    const startDate = document.getElementById('startDate').value;
    if (startDate) {
        const start = new Date(startDate);
        if (end < start) {
            showError(errorRow, errorMessage, 'End date cannot be before start date', endDate);
            return false;
        }
    }
    
    hideError(errorRow, endDate);
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
