/**
 * 문자열의 마지막(끝) 문자의 종성 포함 여부 확인
 * @param value - Target String
 * @returns 종성 포함 여부
 */
function hasCoda(value) {

    console.log("종성 검사할 value: " , value);

    return ((value.charCodeAt(value.length - 1) - 0xAC00) % 28) > 0;
}


/**
 * 필드(Elemenet) null 유효성 검사
 * @param target - 검사 대상 Element,,원래 mouse가 있던 곳!!
 * @param fieldName - 필드명
 * @param focusTarget - 포커스 대상 Element
 * @returns 필드 입력(선택) 여부
 */
function isValid(target, fieldName) {

    console.log(`${target}, ${fieldName}에 대한 기본 유효성 검사`);

    if (target.value.trim()) {
        return true;
    }

    const particle = (hasCoda(fieldName)) ? '을' : '를'; // 조사

    const elementType = (target.type === 'text' || target.type === 'password' || target.type === 'search' || target.type === 'textarea') ? '입력' : '선택';

    alert( `${fieldName + particle} ${elementType}해 주세요.` );

    // target.value = '';
    // ( !focusTarget ? target : focusTarget).focus();
    // throw new Error(`"${target.id}" is required...`)
    target.focus();
    throw new Error(`"${target.id}" 의 값을 입력해주세요`);
}


/**
 * 필드(Elemenet) 회원가입폼 유효성검사
 * @param form
 * @returns 유효성
 */
function validateForm(form) {
    const id = form.userLoginId.value.trim();
    const password = form.userPassword.value.trim();
    const birthday = form.birthday.value;
    const phoneNumber = form.phone.value;
    const cleanedPhoneNumber = phoneNumber.replace(/[-_.\s]/g, ''); // 허용된 문자 제거 후 숫자만 남김

    // 나이 검사
    // 입력값이 비어있지 않은지 먼저 확인
    if (birthday !== '') {
        const age = calculateKoreanAge(birthday);
        // 입력값이 숫자가 아니거나 0보다 작거나 120보다 크면 경고
        if (age < 0 || age > 120) {
            alert('나이는 0살이상 120살 이하여야 합니다.');
            return false;
        }
    } else {
        // 입력값이 비어있을 경우의 처리 (필요에 따라 처리를 추가하세요)
    }

    // 전화번호 검사
    // 전화번호가 비어있는지 먼저 확인
    if (cleanedPhoneNumber.trim() !== '') {
        // 숫자, -, _, 공백, . 이외의 문자를 제거
        var numericPhoneNumber = cleanedPhoneNumber.replace(/[^0-9]/g, '');

        // 순수 숫자만 남긴 전화번호의 길이가 11자리 이하인지 검사
        if (!/^\d{1,11}$/.test(numericPhoneNumber)) {
            alert('전화번호는 숫자, -, _, 공백, . 만 포함할 수 있으며, 숫자만 뽑았을 때 11자리 이하여야 합니다.');
            return false;
        }
    } else {
        // 전화번호 입력값이 비어있을 경우의 처리 (필요에 따라 처리를 추가하세요)
    }

    return true;
}

// 한국나이 계산하여 반환하는 역할
function calculateKoreanAge(birthday) {

    // (YYYY-MM-DD) 형태의,,문자열에서,,(연, 월, 일 data)로 분리하는 역할
    const year = birthday.substring(0, 4);
    const month = birthday.substring(5, 7);
    const day = birthday.substring(8);

    // 주의: Date 객체의 월(month)은 0부터 시작하므로, 실제 월보다 1을 빼주어야 함
    const birthdayDate = new Date(year, month - 1, day);

    const today = new Date();

    const thisYear = today.getFullYear();

    const birthYear = birthdayDate.getFullYear();

    // 한국 나이 계산
    const koreanAge = thisYear - birthYear + 1;

    return koreanAge;
}

/**
 * 데이터 조회
 * @param uri - API Request URI
 * @param params - Parameters
 * @returns json - 결과 데이터
 */
function getJson(uri, params) {

    let json = {} // empty 객체 리터럼

    $.ajax({
        url : uri,
        type : 'get',
        dataType : 'json',
        data : params,
        async : false,
        success : function (response) {

            // console.log("response" + response);

            json = response;

            // console.log("after json에 할당" + json);
        },
        error : function (request, status, error) {
            console.log(error)
        }
    })

    return json;
}

/**
 * 데이터 저장/수정/삭제
 * @param uri - API Request URI
 * @param method - API Request Method
 * @param params - Parameters
 * @returns json - 결과 데이터
 */
function callApi(uri, method, params) {

    console.log(`callApi호출. uri=${uri}, method=${method}, params=${params}`);

    let json = {}

     $.ajax({
        url : uri,
        type : method,
        contentType : 'application/json; charset=utf-8',
        dataType : 'json',
        data : (params) ? JSON.stringify(params) : {},
        async : false,
        success : function (response) {
            json = response;

            // alert('회원정보 수정되었습니다.');
        },
        error : function (request, status, error) {
            console.log(error)
        }
    })

    return json;
}
