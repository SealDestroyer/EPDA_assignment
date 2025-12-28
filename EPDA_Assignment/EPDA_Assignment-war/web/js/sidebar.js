// Sidebar Navigation Functionality
document.addEventListener('DOMContentLoaded', function() {
    // Highlight active navigation item based on current page
    const navItems = document.querySelectorAll('.nav-item');
    const currentPage = window.location.pathname.split('/').pop();
    
    navItems.forEach(item => {
        const href = item.getAttribute('href');
        if (href && (href === currentPage || href.includes(currentPage))) {
            item.classList.add('active');
        }
    });
    
    // Add click handlers to navigation items
    navItems.forEach(item => {
        item.addEventListener('click', function(e) {
            navItems.forEach(nav => nav.classList.remove('active'));
            this.classList.add('active');
        });
    });
});
