function close_notify() {
    const notifications = document.getElementsByClassName("notification");

    Array.from(notifications).forEach(notification => {
        const closeBtn = notification.querySelector('.close-btn');
        if (closeBtn) {
            closeBtn.addEventListener('click', function () {
                notification.remove();
            });
        }
        setTimeout(function () {
            notification.remove();
        }, 3000);
    });
}