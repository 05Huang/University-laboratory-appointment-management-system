$(function() {
    // 获取DOM元素
    const door = $('.door');
    const person = $('.person');
    const doorStatus = $('#doorStatus');
    const authStatus = $('#authStatus');
    const btnSimulate = $('#btnSimulate');
    const btnAuth = $('#btnAuth');
    const btnReset = $('#btnReset');
    const cardReader = $('.card-reader');

    // 状态变量
    let isAuthorized = false;
    let isDoorOpen = false;
    let isAnimating = false;

    // 更新状态标签样式
    function updateStatusBadges() {
        doorStatus
            .removeClass('badge-secondary badge-success')
            .addClass(isDoorOpen ? 'badge-success' : 'badge-secondary')
            .text(isDoorOpen ? '已开启' : '已关闭');

        authStatus
            .removeClass('badge-danger badge-success')
            .addClass(isAuthorized ? 'badge-success' : 'badge-danger')
            .text(isAuthorized ? '已授权' : '未授权');
    }

    // 刷卡动画效果
    function cardReaderAnimation() {
        cardReader.addClass('animate__animated animate__pulse');
        setTimeout(() => {
            cardReader.removeClass('animate__animated animate__pulse');
        }, 1000);
    }

    // 模拟授权
    btnAuth.click(function() {
        if (!isAnimating) {
            isAuthorized = !isAuthorized;
            updateStatusBadges();
            
            $(this)
                .removeClass(isAuthorized ? 'btn-success' : 'btn-danger')
                .addClass(isAuthorized ? 'btn-danger' : 'btn-success')
                .html(`<i class="fa fa-${isAuthorized ? 'times-circle' : 'check-circle'}"></i> ${isAuthorized ? '取消授权' : '模拟授权'}`);

            $.notify({
                icon: isAuthorized ? 'fa fa-check' : 'fa fa-ban',
                message: isAuthorized ? '门禁授权成功' : '已取消门禁授权'
            }, {
                type: isAuthorized ? 'success' : 'warning',
                animate: {
                    enter: 'animate__animated animate__fadeInDown',
                    exit: 'animate__animated animate__fadeOutUp'
                },
                placement: {
                    from: "top",
                    align: "center"
                }
            });
        }
    });

    // 模拟刷卡
    btnSimulate.click(function() {
        if (isAnimating) return;
        
        cardReaderAnimation();
        
        if (!isAuthorized) {
            $.notify({
                icon: 'fa fa-exclamation-triangle',
                message: '未授权，无法通行'
            }, {
                type: 'danger',
                animate: {
                    enter: 'animate__animated animate__shakeX',
                    exit: 'animate__animated animate__fadeOut'
                }
            });
            return;
        }

        isAnimating = true;
        isDoorOpen = true;
        updateStatusBadges();
        
        // 开门动画
        door.addClass('open');
        
        // 人物移动动画序列
        setTimeout(() => {
            person.addClass('enter');
        }, 800);

        // 关门动画
        setTimeout(() => {
            door.removeClass('open');
            isDoorOpen = false;
            updateStatusBadges();
            
            setTimeout(() => {
                isAnimating = false;
            }, 800);
        }, 2500);
    });

    // 重置状态
    btnReset.click(function() {
        if (isAnimating) return;
        
        // 重置所有状态
        door.removeClass('open');
        person.removeClass('enter');
        isAuthorized = false;
        isDoorOpen = false;
        updateStatusBadges();
        
        // 重置授权按钮
        btnAuth
            .removeClass('btn-danger')
            .addClass('btn-success')
            .html('<i class="fa fa-check-circle"></i> 模拟授权');
        
        // 显示重置通知
        $.notify({
            icon: 'fa fa-refresh',
            message: '已重置所有状态'
        }, {
            type: 'info',
            animate: {
                enter: 'animate__animated animate__fadeInDown',
                exit: 'animate__animated animate__fadeOutUp'
            }
        });
    });

    // 初始化状态标签
    updateStatusBadges();
}); 