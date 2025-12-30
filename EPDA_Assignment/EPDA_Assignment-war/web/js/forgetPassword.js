// Validate entire form
function validateForm() {
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();
    const isSecretKeyValid = validateSecretKey();
    
    return isEmailValid && isPasswordValid && isSecretKeyValid;
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
    
    if (password.value.length < 8) {
        showError(errorRow, errorMessage, 'Password must be at least 8 characters long', password);
        return false;
    }
    
    if (!/(?=.*[a-z])/.test(password.value)) {
        showError(errorRow, errorMessage, 'Password must contain at least one lowercase letter', password);
        return false;
    }
    
    if (!/(?=.*[A-Z])/.test(password.value)) {
        showError(errorRow, errorMessage, 'Password must contain at least one uppercase letter', password);
        return false;
    }
    
    if (!/(?=.*\d)/.test(password.value)) {
        showError(errorRow, errorMessage, 'Password must contain at least one number', password);
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

// Validate Secret Key
function validateSecretKey() {
    const secretKey = document.getElementById('secretKey');
    const errorRow = document.getElementById('secretKey-error');
    const errorMessage = document.getElementById('secretKey-error-message');
    
    if (!secretKey.value) {
        showError(errorRow, errorMessage, 'Secret key is required', secretKey);
        return false;
    }
    
    if (!/^\d{6}$/.test(secretKey.value)) {
        showError(errorRow, errorMessage, 'Secret key must be exactly 6 digits', secretKey);
        return false;
    }
    
    hideError(errorRow, secretKey);
    return true;
}