// Form validation functions for Add Lecturer form

// Main validation function
function validateForm() {
    let isValid = true;
    
    isValid = validateFullName() && isValid;
    isValid = validatePassword() && isValid;
    isValid = validateGender() && isValid;
    isValid = validatePhone() && isValid;
    isValid = validateICNumber() && isValid;
    isValid = validateEmail() && isValid;
    isValid = validateAddress() && isValid;
    isValid = validateEmploymentType() && isValid;
    isValid = validateAcademicRank() && isValid;
    
    return isValid;
}

// Validate Full Name
function validateFullName() {
    const fullName = document.getElementById('fullName');
    const errorRow = document.getElementById('fullName-error');
    const errorMessage = document.getElementById('fullName-error-message');
    
    if (!fullName.value.trim()) {
        showError(errorRow, errorMessage, 'Full name is required', fullName);
        return false;
    }
    
    if (fullName.value.trim().length < 3) {
        showError(errorRow, errorMessage, 'Full name must be at least 3 characters long', fullName);
        return false;
    }
    
    if (!/^[a-zA-Z\s]+$/.test(fullName.value.trim())) {
        showError(errorRow, errorMessage, 'Full name should only contain letters and spaces', fullName);
        return false;
    }
    
    hideError(errorRow, fullName);
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

// Validate Gender
function validateGender() {
    const gender = document.getElementById('gender');
    const errorRow = document.getElementById('gender-error');
    const errorMessage = document.getElementById('gender-error-message');
    
    if (!gender.value) {
        showError(errorRow, errorMessage, 'Please select a gender', gender);
        return false;
    }
    
    hideError(errorRow, gender);
    return true;
}

// Validate Phone
function validatePhone() {
    const phone = document.getElementById('phone');
    const errorRow = document.getElementById('phone-error');
    const errorMessage = document.getElementById('phone-error-message');
    
    if (!phone.value.trim()) {
        showError(errorRow, errorMessage, 'Phone number is required', phone);
        return false;
    }
    
    if (!/^[0-9]{10}$/.test(phone.value)) {
        showError(errorRow, errorMessage, 'Phone number must be exactly 10 digits', phone);
        return false;
    }
    
    hideError(errorRow, phone);
    return true;
}

// Validate IC Number
function validateICNumber() {
    const icNumber = document.getElementById('icNumber');
    const errorRow = document.getElementById('icNumber-error');
    const errorMessage = document.getElementById('icNumber-error-message');
    
    if (!icNumber.value.trim()) {
        showError(errorRow, errorMessage, 'IC number is required', icNumber);
        return false;
    }
    
    // Remove hyphens for validation
    const icDigits = icNumber.value.replace(/-/g, '');
    
    // Malaysian IC format: YYMMDD-PB-###G (12 digits)
    if (!/^\d{12}$/.test(icDigits)) {
        showError(errorRow, errorMessage, 'IC number must be 12 digits (format: YYMMDD-PB-###G)', icNumber);
        return false;
    }
    
    // Validate date portion (first 6 digits)
    const year = parseInt(icDigits.substring(0, 2));
    const month = parseInt(icDigits.substring(2, 4));
    const day = parseInt(icDigits.substring(4, 6));
    
    if (month < 1 || month > 12) {
        showError(errorRow, errorMessage, 'Invalid month in IC number', icNumber);
        return false;
    }
    
    if (day < 1 || day > 31) {
        showError(errorRow, errorMessage, 'Invalid day in IC number', icNumber);
        return false;
    }
    
    hideError(errorRow, icNumber);
    return true;
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
    
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!emailPattern.test(email.value.trim())) {
        showError(errorRow, errorMessage, 'Please enter a valid email address', email);
        return false;
    }
    
    hideError(errorRow, email);
    return true;
}

// Validate Address
function validateAddress() {
    const address = document.getElementById('address');
    const errorRow = document.getElementById('address-error');
    const errorMessage = document.getElementById('address-error-message');
    
    if (!address.value.trim()) {
        showError(errorRow, errorMessage, 'Address is required', address);
        return false;
    }
    
    if (address.value.trim().length < 10) {
        showError(errorRow, errorMessage, 'Address must be at least 10 characters long', address);
        return false;
    }
    
    hideError(errorRow, address);
    return true;
}

// Validate Employment Type
function validateEmploymentType() {
    const employmentType = document.getElementById('employmentType');
    const errorRow = document.getElementById('employmentType-error');
    const errorMessage = document.getElementById('employmentType-error-message');
    
    if (!employmentType.value) {
        showError(errorRow, errorMessage, 'Please select an employment type', employmentType);
        return false;
    }
    
    hideError(errorRow, employmentType);
    return true;
}

// Validate Academic Rank
function validateAcademicRank() {
    const academicRank = document.getElementById('academicRank');
    const errorRow = document.getElementById('academicRank-error');
    const errorMessage = document.getElementById('academicRank-error-message');
    
    if (!academicRank.value) {
        showError(errorRow, errorMessage, 'Please select an academic rank', academicRank);
        return false;
    }
    
    hideError(errorRow, academicRank);
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
