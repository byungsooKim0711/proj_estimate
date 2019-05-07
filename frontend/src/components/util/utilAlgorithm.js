const utilAlgorithm = {

    // 숫자 (19650000) -> 한글변환 (일금 일천구백육십오만원정)
    number2Kor ( strNumber ) { 
        strNumber = Math.floor(strNumber).toString();
        strNumber = strNumber.replace(new RegExp(",", "g"), "");

        var arrayAmt = new Array("일", "이", "삼", "사", "오", "육", "칠", "팔", "구", "십"); 
        var arraypos = new Array("", "십", "백", "천"); 
        var arrayUnit = new Array("", "만", "억", "조", "경", "해");

        var pos = strNumber.length%4; 
        var len = (strNumber.length/4).toString();
        if( len.indexOf(".") > 0 )  {
            var unit = len.substring(0, len.indexOf("."));
        }
        else {
            var unit = strNumber.length/4-1;
        }

        var korNumber = "일금 "; 
        var op = 0;

        for(let i=0; i<strNumber.length; i++ ) { 
            if(pos==0) {
                pos=4;
            } 
            var num = parseInt( strNumber.substring( i, i+1 ) ); 
            if( num != 0 ) { 
                korNumber += arrayAmt[ num-1 ]; 
                korNumber += arraypos[ pos-1 ]; 
                op=1; 
            } 
            if(pos == 1) { 
                if(op == 1) {
                    korNumber += arrayUnit[unit]; 
                } 
                unit--; 
                op = 0; 
            } 
            pos--; 
        }

        if (korNumber.length == 0 || korNumber.length == null) {
            return  ""; 
        }
        else {
            return korNumber + "원정" ; 
        } 
    },

    // 19940711 -> 19,940,711
    priceWithCommas ( price ) {
        return Math.floor(price).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },

    roundUp ( number, digit ) {

        if(Math.ceil(number).toString().length <= digit) {
            return number;
        }

        let div = 1;
        for (let i=0; i<digit; i++) {
            div = div * 10;
        }
        
        return Math.ceil(number/div)*div;
    },
}

export default {
    utilAlgorithm
}