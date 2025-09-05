// 初始化GSAP
gsap.registerPlugin();

// DOM元素
const building = document.querySelector('.building');
const accessPanel = document.getElementById('accessPanel');
const scanButton = document.getElementById('scanButton');
const successModal = document.getElementById('successModal');
const videoElement = document.getElementById('videoElement');
const canvasElement = document.getElementById('canvasElement');
const leftDoor = document.querySelector('.left-door');
const rightDoor = document.querySelector('.right-door');

// 初始化状态
let isZoomedIn = false;
let stream = null;
let isAnimating = false;

// 初始化动画
function initializeAnimations() {
    // 窗户发光动画
    gsap.to('.window', {
        boxShadow: '0 0 20px rgba(0, 168, 255, 0.5)',
        duration: 2,
        repeat: -1,
        yoyo: true,
        stagger: {
            each: 0.2,
            from: "random"
        }
    });
}

// 点击门禁面板时的动画
function handlePanelClick(event) {
    if (!isZoomedIn && event.target === accessPanel) {
        zoomToPanel();
    }
}

// 点击刷脸按钮
function handleScanButtonClick(event) {
    event.preventDefault();
    event.stopPropagation();
    
    if (!stream && !isAnimating) {
        startFaceScan();
    }
}

// 开始人脸扫描
async function startFaceScan() {
    if (isAnimating) return;
    isAnimating = true;
    
    try {
        // 请求摄像头权限
        stream = await navigator.mediaDevices.getUserMedia({
            video: {
                width: { ideal: 1280 },
                height: { ideal: 720 },
                facingMode: "user"
            }
        });
        
        // 设置视频源并显示
        videoElement.srcObject = stream;
        videoElement.style.display = 'block';
        
        // 扫描动画
        gsap.fromTo('.scan-line',
            { y: 0 },
            {
                y: '90%',
                duration: 2,
                repeat: 2,
                yoyo: true,
                ease: "none",
                onComplete: () => {
                    // 停止视频流
                    stopVideoStream();
                    // 模拟验证成功
                    handleSuccessfulScan();
                }
            }
        );
    } catch (error) {
        console.error('Error accessing camera:', error);
        // 如果无法访问摄像头，也模拟成功（用于展示）
        handleSuccessfulScan();
    }
}

// 缩放到门禁面板
function zoomToPanel() {
    isZoomedIn = true;
    
    // 建筑物缩放和移动
    gsap.to(building, {
        duration: 1.5,
        scale: 2,
        y: -100,
        rotationX: 5,
        ease: "power2.inOut"
    });

    // 面板放大
    gsap.to(accessPanel, {
        duration: 1.5,
        scale: 1.2,
        ease: "power2.inOut"
    });

    // 增加景深效果
    gsap.to('.scene', {
        duration: 1.5,
        perspective: 1000,
        ease: "power2.inOut"
    });
}

// 停止视频流
function stopVideoStream() {
    if (stream) {
        stream.getTracks().forEach(track => track.stop());
        stream = null;
        videoElement.srcObject = null;
        videoElement.style.display = 'none';
    }
}

// 处理成功扫描
function handleSuccessfulScan() {
    // 确保不重复触发动画
    if (isAnimating) {
        // 门开启动画
        const timeline = gsap.timeline({
            onComplete: () => {
                setTimeout(() => {
                    hideSuccessModal();
                    isAnimating = false;
                }, 3000);
            }
        });

        // 添加动画序列
        timeline
            .to([leftDoor, rightDoor], {
                duration: 1.5,
                rotateY: (i) => i === 0 ? -90 : 90,
                ease: "power2.inOut"
            })
            .to('.entrance', {
                boxShadow: '0 0 50px rgba(0, 168, 255, 0.8)',
                duration: 1,
                ease: "power2.inOut"
            }, "-=1")
            .call(() => showSuccessModal());
    }
}

// 显示成功提示框
function showSuccessModal() {
    successModal.classList.add('show');
    // 重置动画
    document.querySelector('.success-circle').style.animation = 'none';
    document.querySelector('.success-check').style.animation = 'none';
    
    // 触发重排
    successModal.offsetHeight;
    
    // 重新添加动画
    document.querySelector('.success-circle').style.animation = 'circleAnimation 0.6s ease forwards';
    document.querySelector('.success-check').style.animation = 'checkAnimation 0.6s ease 0.3s forwards';
}

// 隐藏成功提示框
function hideSuccessModal() {
    successModal.classList.remove('show');
}

// 事件监听器设置
function setupEventListeners() {
    // 使用事件委托处理点击
    document.addEventListener('click', (event) => {
        const target = event.target;
        
        // 如果点击的是扫描按钮
        if (target.closest('.scan-button')) {
            handleScanButtonClick(event);
        }
        // 如果点击的是门禁面板
        else if (target.closest('.access-panel')) {
            handlePanelClick(event);
        }
    });
}

// 页面加载完成后初始化
window.addEventListener('load', () => {
    initializeAnimations();
    setupEventListeners();
});

// 页面卸载时清理
window.addEventListener('beforeunload', () => {
    stopVideoStream();
});

// 人脸识别相关的动画和交互逻辑
document.addEventListener('DOMContentLoaded', function() {
    const verifyButton = document.getElementById('verifyButton');
    const modal = document.getElementById('faceRecognitionModal');
    const closeButton = document.getElementById('closeModal');
    const successModal = document.getElementById('successModal');
    const failureModal = document.getElementById('failureModal');
    const leftDoor = document.querySelector('.left-door');
    const rightDoor = document.querySelector('.right-door');
    let stream = null;

    // 打开人脸识别模态框
    verifyButton.addEventListener('click', function() {
        modal.style.display = 'flex';
        startCamera();
    });

    // 关闭模态框
    closeButton.addEventListener('click', function() {
        closeModal();
    });

    // 启动摄像头并在3秒后捕获照片
    async function startCamera() {
        try {
            stream = await navigator.mediaDevices.getUserMedia({ video: true });
            const video = document.getElementById('videoElement');
            video.srcObject = stream;

            // 3秒后捕获照片并发送验证请求
            setTimeout(async () => {
                await captureAndVerify();
            }, 3000);

        } catch (err) {
            console.error('摄像头访问失败:', err);
            showFailure('无法访问摄像头');
        }
    }

    // 关闭模态框和摄像头
    function closeModal() {
        modal.style.display = 'none';
        if (stream) {
            stream.getTracks().forEach(track => track.stop());
        }
    }

    // 语音播报功能
    function speak(text) {
        // 创建语音合成实例
        const utterance = new SpeechSynthesisUtterance(text);
        // 设置语音为中文
        utterance.lang = 'zh-CN';
        // 播放语音
        window.speechSynthesis.speak(utterance);
    }

    // 处理验证结果的语音播报
    function handleVerificationVoice(result) {
        if (result.code === 200 && result.data && result.data.verified && result.data.hasReservation) {
            speak('验证成功，欢迎使用');
        } else if (result.data) {
            if (result.data.verified && !result.data.hasReservation) {
                speak('您没有有效的预约记录');
            } else if (!result.data.verified) {
                speak('人脸识别失败，请先注册人脸信息');
            }
        } else if (result.message) {
            // 处理后端返回的错误消息
            if (result.message.includes('未找到有效的预约记录')) {
                speak('您没有有效的预约记录');
            } else if (result.message.includes('人脸识别失败')) {
                speak('人脸识别失败，请先注册人脸信息');
            } else {
                speak(result.message);
            }
        } else {
            speak('系统错误，请重试');
        }
    }

    // 捕获照片并发送验证请求
    async function captureAndVerify() {
        const video = document.getElementById('videoElement');
        if (!video.srcObject) {
            return;
        }

        const canvas = document.createElement('canvas');
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        const ctx = canvas.getContext('2d');
        ctx.drawImage(video, 0, 0);

        canvas.toBlob(async function(blob) {
            const formData = new FormData();
            formData.append('file', blob, 'face.jpg');

            try {
                const response = await fetch('/api/face-recognition/verify', {
                    method: 'POST',
                    body: formData
                });

                const result = await response.json();
                
                // 添加语音播报
                handleVerificationVoice(result);
                
                if (result.code === 200 && result.data && result.data.verified && result.data.hasReservation) {
                    // 验证成功，先关闭识别模态框
                    closeModal();
                    // 等待模态框关闭动画完成后再显示成功提示
                    setTimeout(() => {
                        showSuccess();
                        animateDoors();
                    }, 300);
                } else {
                    // 根据具体情况显示不同的错误信息
                    let errorMessage;
                    if (result.data) {
                        if (result.data.verified && !result.data.hasReservation) {
                            errorMessage = '您没有有效的预约记录';
                        } else if (!result.data.verified) {
                            errorMessage = '人脸识别失败，请先注册人脸信息';
                        }
                    } else if (result.message) {
                        // 使用后端返回的错误消息
                        errorMessage = result.message;
                    } else {
                        errorMessage = '系统错误，请重试';
                    }
                    showFailure(errorMessage);
                    setTimeout(closeModal, 2000);
                }
            } catch (error) {
                console.error('请求失败:', error);
                speak('网络请求失败，请重试');
                showFailure('网络请求失败');
                setTimeout(closeModal, 2000);
            }
        }, 'image/jpeg');
    }

    // 显示成功提示
    function showSuccess() {
        // 确保其他模态框都已关闭
        modal.style.display = 'none';
        failureModal.style.display = 'none';
        
        // 显示成功提示框
        successModal.style.display = 'flex';
        // 强制重绘
        successModal.offsetHeight;
        successModal.style.opacity = '1';
        successModal.querySelector('.modal-content').style.transform = 'scale(1)';
        
        // 2秒后隐藏成功提示
        setTimeout(() => {
            successModal.style.opacity = '0';
            successModal.querySelector('.modal-content').style.transform = 'scale(0.8)';
            setTimeout(() => {
                successModal.style.display = 'none';
            }, 300);
        }, 2000);
    }

    // 显示失败提示
    function showFailure(message) {
        // 确保其他模态框都已关闭
        modal.style.display = 'none';
        successModal.style.display = 'none';
        
        const failureMessage = failureModal.querySelector('p');
        failureMessage.textContent = message;
        
        // 显示失败提示框
        failureModal.style.display = 'flex';
        // 强制重绘
        failureModal.offsetHeight;
        failureModal.style.opacity = '1';
        failureModal.querySelector('.modal-content').style.transform = 'scale(1)';
        
        // 2秒后隐藏失败提示
        setTimeout(() => {
            failureModal.style.opacity = '0';
            failureModal.querySelector('.modal-content').style.transform = 'scale(0.8)';
            setTimeout(() => {
                failureModal.style.display = 'none';
            }, 300);
        }, 2000);
    }

    // 门的动画
    function animateDoors() {
        leftDoor.style.transform = 'translateX(-100%)';
        rightDoor.style.transform = 'translateX(100%)';
        
        // 5秒后关门
        setTimeout(() => {
            leftDoor.style.transform = 'translateX(0)';
            rightDoor.style.transform = 'translateX(0)';
        }, 5000);
    }
}); 