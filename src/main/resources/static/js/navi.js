const list = document.querySelectorAll('.list');

// 네비게이션 항목에 클릭 이벤트를 추가
list.forEach((item) => item.addEventListener('click', activeLink));

// li 요소를 클릭하면 ==>> click event에 의해 실행되는 함수
function activeLink() {

    list.forEach((item) =>

        // 일단 모든 list 요소의,,속성 class명 active를 제거해 ==>> default화(백지 상태) 시킴
        item.classList.remove('active')
    );

    // (백지 상태)에서,,클릭한 li 요소에만 ==>> 속성 class명 active 추가!!
    this.classList.add('active'); // this 값은 ==>> click event가 발생한 요소임!!

    // 선택한 항목--(a요소의,,속성 href 값)을 ==>>  로컬 스토리지에 저장합니다.
    // localStorage.setItem('selectedNavItem', this.querySelector('.title').textContent);
    localStorage.setItem('selectedNavItem', this.querySelector('a').getAttribute('href'));
}



// 페이지가 로드될 때 실행되는 함수
window.addEventListener('DOMContentLoaded', () => {

    // 저장된 선택한 항목이 있으면 해당 항목을 활성화합니다.
    const selectedNavItem = localStorage.getItem('selectedNavItem');

    const currentPath = window.location.pathname;   //현재브라우저의 뒷부분경로

    if (selectedNavItem) {

        list.forEach((item) => {

            console.log(item);

            if (item.querySelector('a').getAttribute('href') === currentPath) {
                item.classList.add('active');
            }
        });
    } else {

        localStorage.clear();

        goToHomePage();
    }
});

// 홈 화면으로 이동하는 함수
function goToHomePage() {

    // 모든 네비게이션 항목에서 'active' 클래스를 제거합니다.(cf item은 ==>> li 요소)
    list.forEach((item) => item.classList.remove('active'));

    // 홈 화면을 나타내는 네비게이션 항목에 'active' 클래스를 추가합니다.
    document.querySelector('.list a[href="/"]').parentNode.classList.add('active');


    // 선택한 항목을 로컬 스토리지에서 제거합니다.
    localStorage.removeItem('selectedNavItem');
}
