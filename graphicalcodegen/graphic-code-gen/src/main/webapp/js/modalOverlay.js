var modalOverlay;
var modalOverlayCallback;

$(function () {
    modalOverlay = $("#amt-modal-overlay")
        .click(handleModalOverlayClick);
});

function showModalOverlay(callback) {
    modalOverlay.show();
    modalOverlay.css("z-index", 1001);
    modalOverlay.callback = callback;
}

function hideModalOverlay() {
    modalOverlay.hide();
}

function handleModalOverlayClick() {
    modalOverlay.hide();
    if (typeof (modalOverlay.callback) == "function")
        modalOverlay.callback();
}