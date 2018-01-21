jQuery(document).ready(function () {

    handleScrollEvent();

    // Smooth scrolling when click to nav
    jQuery("nav > ul > li.scroll-item > a").click(function (e) {
        e.preventDefault();
        var curLink = jQuery(this);
        var scrollPoint = jQuery(curLink.attr("href")).position().top - 70;
        scrollPoint = scrollPoint < 0 ? 0 : scrollPoint;
        jQuery("body,html").animate({
            scrollTop: scrollPoint
        }, 500);
    });

    // Scroll to top when clicking this button
    jQuery("#scroll-to-top").click(function () {
        jQuery("body,html").animate({
            scrollTop: 0
        }, 500);
        return false;
    });

    // Scroll to bottom
    jQuery(".scroll-to-bottom").click(function () {
        jQuery("body, html").animate({
            scrollTop: jQuery(document).height()
        }, 500);
        return false;
    });

    jQuery(window).scroll(function () {
        handleScrollEvent();
    });

    function handleScrollEvent() {
        // Get current scroll position
        var currentScrollPos = jQuery(this).scrollTop();

        // Navbar shrink when scroll down
        jQuery(".navbar-default").toggleClass("navbar-shrink", currentScrollPos > 50);

        // Handle scroll to top button
        if (currentScrollPos > 50) {
            jQuery("#scroll-to-top").fadeIn(200);
        } else {
            jQuery("#scroll-to-top").fadeOut(200);
        }

        // Iterate through all node
        jQuery("nav > ul > li.scroll-item > a").each(function () {
            var curLink = jQuery(this);
            var refElem = jQuery(curLink.attr("href"));
            //Compare the value of current position and the every section position in each scroll
            if (isPositionOnElement(currentScrollPos, refElem)) {
                // Remove class active in all nav
                jQuery("nav > ul > li").removeClass("active");
                // Add class active
                curLink.parent().addClass("active");
            } else {
                curLink.parent().removeClass("active");
            }
        });
    }

    function isPositionOnElement(pos, elem) {
        var elemTop = Math.floor(elem.position().top) - 70;
        var elemBottom = Math.floor(elem.position().top + elem.height() - 70);
        return pos >= elemTop && pos < elemBottom;
    }

    // Skill bar waypoints
    jQuery("#skill-bar-container").waypoint(function (direction) {
        jQuery(".skill-bar-progress.empty").each(function (index, object) {
            jQuery(this).animate({
                width: jQuery(this).attr("data-percent")
            }, 800, function () {
                // jQuery(this.element).removeClass("empty");
                // alert("som nacital");
            });
        })
    }, {
        offset: "60%"
    });

});