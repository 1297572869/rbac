"use strict";

kanConsole.controller('bannerController', function($scope, $compile) {
    // 初始化上传组件
    var initUpload = function() {
        if ($('#uploadFile').length == 0) {
            setTimeout(initUpload, 100);
        } else {
            $('#uploadFile').fileupload({
                dataType : "json",
                type : 'POST',
                //acceptFileTypes : "*.apk",
                url : contextPath + '/upload_fileBanner.do?dir=banner',
                // autoUpload : false,
                start : function(e, data) {
                    $(".progress-bar-div").fadeIn(200);
                },
                done : function(e, data) {
                    var msg = data.result;
                    if (msg.code == 200) {
                        /*$scope.uploadFileData = {
                            fileId : msg.data.uploadFile.uploadFileId,
                            fileName : msg.data.uploadFile.fileName,
                            versionUrl : msg.data.uploadFile.path
                        };
                        */
                        //$(".fileupload-preview").text(msg.data.uploadFile.fileName);
                        $(this).val("");
                        $("#coverPath").val(msg.data.uploadFile.path);
                        $("#coverId").val(msg.data.uploadFile.uploadFileId);
                        $("#coverWidth").val(msg.data.uploadFile.width);
                        $("#coverHeight").val(msg.data.uploadFile.height);
                        $("#imgImg").attr("src", (msg.data.uploadFile.path));
                        $("#bannerControllerDiv").scope().imgShow = true;
                        $("#bannerControllerDiv").scope().$apply();
                    } else {
                        $(".fileupload-preview").text(msg.error);
                    }
                    $(".progress-bar-div").fadeOut(200);
                },
                progressall : function(e, data) {
                    var progress = parseInt(data.loaded / data.total * 100 * 0.5, 10);
                    if (progress == 50) {
                        var progressGo = function(widthNum) {
                            if (widthNum < 100) {
                                $('.progress-bar').css('width', (++widthNum) + '%');
                                setTimeout(function() {
                                    progressGo(widthNum);
                                }, 10);
                            } else {
                                return;
                            }

                        }
                        progressGo(50);
                    }
                    $('.progress-bar').css('width', progress + '%');
                }
            })
            return;
        }
    };

    //添加
    $scope.addBanner = function(){
        $("#bannerControllerDiv").scope().imgShow = false;
        $scope.banner = {};
        $(':input', "#addBannerForm").not(':button,:submit,:reset').val('').removeAttr('checked');
        validator.resetForm();
        $("#addBannerForm").validate().resetForm();
        $(".has-error").removeClass("has-error");
        $('.alert-danger', "#addBannerForm").hide();
        $("#addBannerModal").modal('show');
        initUpload();

    };

    //编辑
    $scope.editBanner = function(id){
        $.ajax({
            type: "GET",
            url: contextPath + "/admin/get_banner.do",
            data: "id=" + id,
            dataType: "json",
            async: false,
            success: function(msg){
                $("#addBannerForm").validate().resetForm();
                $(".has-error").removeClass("has-error");
                $('.alert-danger', "#addBannerForm").hide();
                $scope.banner = msg.data.banner;
                $("#bannerControllerDiv").scope().imgShow = true;
                $('#addBannerModal').modal('show');
            }
        });
        initUpload();
    };

    $(function(){
        $('#bannerList').bind('draw.dt',function(){
            var linkFn = $compile($('#bannerList'));
            linkFn($scope);
        });
    });
});

