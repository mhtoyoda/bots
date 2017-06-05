/**
 * Created by valdisnei on 10/03/17.
 */

var Bot = Bot || {};

Bot.Upload = (function () {

    function UploadProcess() {
        inicializaDropzone();
    }

    Upload.prototype.iniciar = function () {
        // this.submitButton.on('click', onUpload.bind(this));
    };



    function inicializaDropzone() {
    };

    return Upload;

}());


$(function () {
    var upload = new Bot.Upload();
    upload.iniciar();
});





