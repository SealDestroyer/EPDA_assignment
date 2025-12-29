// Form validation functions for Add Grade form

// Main validation function
function validateForm() {
    let isValid = true;
    
    isValid = validateGradeLetter() && isValid;
    isValid = validateMinPercentage() && isValid;
    isValid = validateMaxPercentage() && isValid;
    
    return isValid;
}

// Validate Grade Letter
function validateGradeLetter() {
    const gradeLetter = document.getElementById('gradeLetter');
    const errorRow = document.getElementById('gradeLetter-error');
    const errorMessage = document.getElementById('gradeLetter-error-message');
    
    if (!gradeLetter.value.trim()) {
        showError(errorRow, errorMessage, 'Grade letter is required', gradeLetter);
        return false;
    }
    
    // Allow single letter (A-F) optionally followed by + or -
    if (!/^[A-Fa-f][+\-]?$/.test(gradeLetter.value.trim())) {
        showError(errorRow, errorMessage, 'Grade letter must be a letter (A-F) optionally followed by + or -', gradeLetter);
        return false;
    }
    
    hideError(errorRow, gradeLetter);
    return true;
}

// Validate Min Percentage
function validateMinPercentage() {
    const minPercentage = document.getElementById('minPercentage');
    const errorRow = document.getElementById('minPercentage-error');
    const errorMessage = document.getElementById('minPercentage-error-message');
    
    if (!minPercentage.value.trim()) {
        showError(errorRow, errorMessage, 'Minimum percentage is required', minPercentage);
        return false;
    }
    
    const minValue = parseFloat(minPercentage.value);
    
    if (isNaN(minValue)) {
        showError(errorRow, errorMessage, 'Minimum percentage must be a valid number', minPercentage);
        return false;
    }
    
    if (minValue < 0 || minValue > 100) {
        showError(errorRow, errorMessage, 'Minimum percentage must be between 0 and 100', minPercentage);
        return false;
    }
    
    hideError(errorRow, minPercentage);
    return true;
}

// Validate Max Percentage
function validateMaxPercentage() {
    const maxPercentage = document.getElementById('maxPercentage');
    const minPercentage = document.getElementById('minPercentage');
    const errorRow = document.getElementById('maxPercentage-error');
    const errorMessage = document.getElementById('maxPercentage-error-message');
    
    if (!maxPercentage.value.trim()) {
        showError(errorRow, errorMessage, 'Maximum percentage is required', maxPercentage);
        return false;
    }
    
    const maxValue = parseFloat(maxPercentage.value);
    
    if (isNaN(maxValue)) {
        showError(errorRow, errorMessage, 'Maximum percentage must be a valid number', maxPercentage);
        return false;
    }
    
    if (maxValue < 0 || maxValue > 100) {
        showError(errorRow, errorMessage, 'Maximum percentage must be between 0 and 100', maxPercentage);
        return false;
    }
    
    // Check if max is greater than min
    const minValue = parseFloat(minPercentage.value);
    if (!isNaN(minValue) && maxValue <= minValue) {
        showError(errorRow, errorMessage, 'Maximum percentage must be greater than minimum percentage', maxPercentage);
        return false;
    }
    
    hideError(errorRow, maxPercentage);
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
