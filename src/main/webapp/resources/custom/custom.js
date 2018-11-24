$( window ).scroll(function() {
    var isActive = $(".custom-select-menu-panel").is(':visible');
    if(isActive) {
        $(".custom-select-menu-panel").position({
            my: "left top",
            at: "left bottom",
            of: $(".custom-select-menu")
        });
    }
});