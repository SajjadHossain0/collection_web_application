document.addEventListener("DOMContentLoaded", function () {
    // Get the saved theme from local storage or default to light
    let theme = localStorage.getItem('theme') || 'light';

    // Apply the theme on page load
    applyTheme(theme);

    // Change the theme based on the current setting
    document.getElementById('theme-toggle').addEventListener('click', function () {
        theme = theme === 'light' ? 'dark' : 'light';
        applyTheme(theme);
        localStorage.setItem('theme', theme); // Save the selected theme to local storage
    });
});

function applyTheme(theme) {
    document.body.className = ''; // Clear any previous theme classes
    document.getElementById('main-navbar').className = 'navbar navbar-expand-lg shadow-sm'; // Reset navbar classes

    document.body.classList.add(theme);
    document.getElementById('main-navbar').classList.add(theme);

    // Update the icon based on the theme
    const themeIcon = document.getElementById('theme-icon');
    if (theme === 'light') {
        themeIcon.classList.remove('fa-sun');
        themeIcon.classList.add('fa-moon');
    } else {
        themeIcon.classList.remove('fa-moon');
        themeIcon.classList.add('fa-sun');
    }



    // Apply theme to cards
    const cards = document.querySelectorAll('#main-card');
    cards.forEach(card => {
        card.className = 'card'; // Reset card classes
        card.classList.add(theme); // Apply current theme to card
    });
    //modal-content
    const modal_contents = document.querySelectorAll('#modal-content');
    modal_contents.forEach(modal_content => {
        modal_content.className = 'modal-content';
        modal_content.classList.add(theme);
    });

    // Apply theme to user_table in admin page
    const user_tables = document.querySelectorAll('#user-table');
    user_tables.forEach(user_table => {
        user_table.className = 'table';
        user_table.classList.add(theme);
    });
}

