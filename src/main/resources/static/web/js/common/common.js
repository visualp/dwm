//ajax token 처리
$(function(){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    //ajax token 처리

    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

    /* 2018-12-17 고병만
     *  csrf multipart/form-data 보안
     * */
    $("form").each(function(){
        if( $(this).attr("enctype")=="multipart/form-data"){
            var param = getUrlVars( $(this).attr("action") );
            if(Object.keys(param).length > 0 ){
                var action = $(this).attr("action") +  "&_csrf=" + token;
            }else{
                var action = $(this).attr("action") +  "?_csrf=" + token;
            }
            $(this).attr("action", action) ;
        }
    });
});

/**
 * logout 처리
 */
function fn_logout(){
    $("#logoutfrm").submit();
}

/**
 * https://html-online.com/articles/get-url-parameters-javascript/
 * Url String  convert object parameter value
 * [사용방법]
 * var number = getUrlVars()["x"];
 * var mytext = getUrlVars()["text"];
 */
function getUrlVars(str_url) {
    var vars = {};
    var parts = str_url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

//비밀번호 체크
//http://gongam100.tistory.com/24
//영문 + 숫자 + 특수문자 혼합 사용

function check_password(password, id){

    var pw = password;
    var num = pw.search(/[0-9]/g);
    var eng = pw.search(/[a-z]/ig);
    var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

    if(pw.length < 8 || pw.length > 20){
        alert("8자리 ~ 20자리 이내로 입력해주세요.");
        return false;
    }

    if(pw.search(/₩s/) != -1){
        alert("비밀번호는 공백없이 입력해주세요.");
        return false;
    }

    if(num < 0 || eng < 0 || spe < 0 ){
        alert("영문,숫자,특수문자를 혼합하여 입력해주세요.");
        return false;
    }

    if(/(\w)\1\1\1/.test(pw)){
        alert('같은 문자를 4번 이상 사용할 수 없습니다.');
        return false;
    }

    if(pw.search(id) > -1){
        alert("비밀번호에 아이디를 사용할 수 없습니다.");
        return false;
    }

    return true;
}


function loading(){
    $(".dimmer").addClass("active");
    $("button").prop("disabled",true);
    $(".loader").focus();
}

function complete(){
    $(".dimmer").removeClass("active");
    $("button").prop("disabled",false);
    $(".loader").blur();
}

// window popup
function openPopup(url, width, height, target, scrollbars){
    var cw=screen.availWidth;     //화면 넓이
    var ch=screen.availHeight;    //화면 높이

    if(!scrollbars){
        scrollbars = "no";
    }

    var sw=width;    //띄울 창의 넓이
    var sh=height;    //띄울 창의 높이

    var ml=(cw-sw)/2;        //가운데 띄우기위한 창의 x위치
    var mt=(ch-sh)/2;         //가운데 띄우기위한 창의 y위치
    window.open(url, target, 'width='+sw+',height='+sh+',top='+mt+',left='+ml+',resizable=no,scrollbars='+scrollbars);
}

function addDate(nday) {

    let date = new Date();
    date.setDate(date.getDate() + nday);
    let year = date.getFullYear();
    let month = date.getMonth() + 1
    let day = date.getDate();

    if (month < 10) {
        month = "0" + month;
    }

    if (day < 10) {
        day = "0" + day;
    }

    return year + "" + month + "" + day;
}

function removeExp(str) {
    let regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
    return str.replace(regExp, "");
}

function common_loading() {
    $("#common-loading").removeClass("d-none");
    $("button").prop("disabled", true);
}

function common_complete() {
    $("#common-loading").addClass("d-none");
    $("button").prop("disabled", false);
}

/*
 * http://shuiky.tistory.com/entry/jQuery%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%ED%85%8C%EC%9D%B4%EB%B8%94-%EC%85%80%EB%B3%91%ED%95%A9?category=331844
 * 같은 값이 있는 열을 병합함
 *
 * 사용법 : $('#테이블 ID').rowspan(0);
 *
 */
$.fn.rowspan = function(colIdx, isStats) {
    return this.each(function(){
        var that;
        $('tr', this).each(function(row) {
            $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {

                if ($(this).html() == $(that).html()
                    && (!isStats
                        || isStats && $(this).prev().html() == $(that).prev().html()
                    )
                ) {
                    rowspan = $(that).attr("rowspan") || 1;
                    rowspan = Number(rowspan)+1;

                    $(that).attr("rowspan",rowspan);

                    // do your action for the colspan cell here
                    $(this).hide();

                    //$(this).remove();
                    // do your action for the old cell here

                } else {
                    that = this;
                }

                // set the that if not already set
                that = (that == null) ? this : that;
            });
        });
    });
};

/*
 *
 * 같은 값이 있는 행을 병합함
 *
 * 사용법 : $('#테이블 ID').colspan (0);
 *
 */
$.fn.colspan = function(rowIdx) {
    return this.each(function(){

        var that;
        $('tr', this).filter(":eq("+rowIdx+")").each(function(row) {
            $(this).find('th').filter(':visible').each(function(col) {
                if ($(this).html() == $(that).html()) {
                    colspan = $(that).attr("colSpan") || 1;
                    colspan = Number(colspan)+1;

                    $(that).attr("colSpan",colspan);
                    $(this).hide(); // .remove();
                } else {
                    that = this;
                }

                // set the that if not already set
                that = (that == null) ? this : that;

            });
        });
    });
}