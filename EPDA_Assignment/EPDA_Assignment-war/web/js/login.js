// Validate entire form
function validateForm() {
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();
    
    return isEmailValid && isPasswordValid;
}

// Validate Email
function validateEmail() {
    const email = document.getElementById('email');
    const errorRow = document.getElementById('email-error');
    const errorMessage = document.getElementById('email-error-message');
    
    if (!email.value.trim()) {
        showError(errorRow, errorMessage, 'Email is required', email);
        return false;
    }
    
    const emailPattern = /^[a-zA-Z0-9._-]+@apu\.edu\.my$/;
    if (!emailPattern.test(email.value.trim())) {
        showError(errorRow, errorMessage, 'Please enter a valid APU email address (@apu.edu.my)', email);
        return false;
    }
    
    hideError(errorRow, email);
    return true;
}

// Validate Password
function validatePassword() {
    const password = document.getElementById('password');
    const errorRow = document.getElementById('password-error');
    const errorMessage = document.getElementById('password-error-message');
    
    if (!password.value) {
        showError(errorRow, errorMessage, 'Password is required', password);
        return false;
    }
    
    hideError(errorRow, password);
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