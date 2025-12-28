// Footer Functionality
document.addEventListener('DOMContentLoaded', function() {
    // Update footer year dynamically
    const footer = document.querySelector('.footer p');
    if (footer) {
        const currentYear = new Date().getFullYear();
        footer.textContent = `© ${currentYear} Admin Dashboard. All rights reserved.`;
    }
});
