// Form validation functions for Add Class form

// Main validation function
function validateForm() {
    let isValid = true;
    
    isValid = validateClassName() && isValid;
    isValid = validateSemester() && isValid;
    isValid = validateAcademicYear() && isValid;
    isValid = validateAcademicLeader() && isValid;
    
    return isValid;
}

// Validate Class Name
function validateClassName() {
    const className = document.getElementById('className');
    const errorRow = document.getElementById('className-error');
    const errorMessage = document.getElementById('className-error-message');
    
    if (!className.value.trim()) {
        showError(errorRow, errorMessage, 'Class name is required', className);
        return false;
    }
    
    if (className.value.trim().length < 2) {
        showError(errorRow, errorMessage, 'Class name must be at least 2 characters long', className);
        return false;
    }
    
    if (className.value.trim().length > 50) {
        showError(errorRow, errorMessage, 'Class name must not exceed 50 characters', className);
        return false;
    }
    
    hideError(errorRow, className);
    return true;
}

// Validate Semester
function validateSemester() {
    const semester = document.getElementById('semester');
    const errorRow = document.getElementById('semester-error');
    const errorMessage = document.getElementById('semester-error-message');
    
    if (!semester.value.trim()) {
        showError(errorRow, errorMessage, 'Semester is required', semester);
        return false;
    }
    
    // Check if it's a valid semester format (1, 2, or 3)
    const semesterValue = semester.value.trim();
    
    if (!/^[1-3]$/.test(semesterValue)) {
        showError(errorRow, errorMessage, 'Semester must be 1, 2, or 3', semester);
        return false;
    }
    
    hideError(errorRow, semester);
    return true;
}

// Validate Academic Year
function validateAcademicYear() {
    const academicYear = document.getElementById('academicYear');
    const errorRow = document.getElementById('academicYear-error');
    const errorMessage = document.getElementById('academicYear-error-message');
    
    if (!academicYear.value.trim()) {
        showError(errorRow, errorMessage, 'Academic year is required', academicYear);
        return false;
    }
    
    // Check for valid academic year format (e.g., "2024/2025")
    const academicYearPattern = /^20\d{2}\/20\d{2}$/;
    
    if (!academicYearPattern.test(academicYear.value.trim())) {
        showError(errorRow, errorMessage, 'Academic year must be in format YYYY/YYYY (e.g., 2024/2025)', academicYear);
        return false;
    }
    
    // Validate that the second year is exactly one year after the first
    const years = academicYear.value.trim().split('/');
    const firstYear = parseInt(years[0]);
    const secondYear = parseInt(years[1]);
    
    if (secondYear !== firstYear + 1) {
        showError(errorRow, errorMessage, 'Second year must be exactly one year after the first year', academicYear);
        return false;
    }
    
    // Validate years are reasonable (not too far in the past or future)
    const currentYear = new Date().getFullYear();
    if (firstYear < currentYear - 5 || firstYear > currentYear + 5) {
        showError(errorRow, errorMessage, 'Academic year must be within 5 years of current year', academicYear);
        return false;
    }
    
    hideError(errorRow, academicYear);
    return true;
}

// Validate Academic Leader
function validateAcademicLeader() {
    const academicLeader = document.getElementById('assignedAcademicLeaderID');
    const errorRow = document.getElementById('assignedAcademicLeaderID-error');
    const errorMessage = document.getElementById('assignedAcademicLeaderID-error-message');
    
    if (!academicLeader.value) {
        showError(errorRow, errorMessage, 'Please select an academic leader', academicLeader);
        return false;
    }
    
    hideError(errorRow, academicLeader);
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
