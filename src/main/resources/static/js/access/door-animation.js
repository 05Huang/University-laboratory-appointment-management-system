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

document.addEventListener('DOMContentLoaded', function() {
    // 获取DOM元素
    const verifyButton = document.getElementById('verifyButton');
    const modal = document.getElementById('faceRecognitionModal');
    const closeButton = document.getElementById('closeModal');
    const successModal = document.getElementById('successModal');
    const video = document.getElementById('videoElement');
    const statusText = document.querySelector('.status-text');

    let stream = null;

    // 打开摄像头
    async function startCamera() {
        try {
            stream = await navigator.mediaDevices.getUserMedia({
                video: {
                    width: 640,
                    height: 480,
                    facingMode: "user"
                }
            });
            video.srcObject = stream;
            
            // 3秒后自动拍照并发送
            setTimeout(captureAndVerify, 3000);
        } catch (err) {
            console.error('摄像头访问失败:', err);
            statusText.textContent = '无法访问摄像头';
            setTimeout(closeModal, 2000);
        }
    }

    // 拍照并发送到后端验证
    async function captureAndVerify() {
        try {
            // 创建canvas
            const canvas = document.createElement('canvas');
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            const ctx = canvas.getContext('2d');
            
            // 在canvas上绘制视频帧，需要翻转回来
            ctx.scale(-1, 1); // 水平翻转
            ctx.drawImage(video, -canvas.width, 0, canvas.width, canvas.height);
            ctx.scale(-1, 1); // 恢复变换
            
            // 将canvas内容转换为blob
            const blob = await new Promise(resolve => canvas.toBlob(resolve, 'image/jpeg', 0.95));
            
            // 创建FormData
            const formData = new FormData();
            formData.append('file', blob, 'face.jpg');
            
            console.log('准备发送人脸识别请求');
            statusText.textContent = '正在验证...';
            
            // 发送到后端
            const response = await fetch('/api/face-recognition/verify', {
                method: 'POST',
                body: formData
            });
            
            const result = await response.json();
            console.log('识别结果:', result);
            
            if (result.code === 200 && result.data === true) {
                // 识别成功
                handleSuccess();
            } else {
                // 识别失败
                statusText.textContent = result.message || '识别失败，请重试';
                setTimeout(closeModal, 2000);
            }
        } catch (err) {
            console.error('识别过程出错:', err);
            statusText.textContent = '识别失败，请重试';
            setTimeout(closeModal, 2000);
        }
    }

    // 处理识别成功
    function handleSuccess() {
        console.log('处理识别成功');
        // 关闭摄像头模态框
        closeModal();
        
        // 显示成功提示
        successModal.style.display = 'flex';
        // 添加show类来触发动画
        setTimeout(() => {
            successModal.classList.add('show');
        }, 10);
        
        // 播放开门动画
        playDoorAnimation();
        
        // 3秒后关闭成功提示
        setTimeout(() => {
            successModal.classList.remove('show');
            setTimeout(() => {
                successModal.style.display = 'none';
            }, 300); // 等待动画完成
        }, 3000);
    }

    // 关闭模态框
    function closeModal() {
        modal.style.display = 'none';
        if (stream) {
            stream.getTracks().forEach(track => track.stop());
            stream = null;
        }
    }

    // 播放开门动画
    function playDoorAnimation() {
        const leftDoor = document.querySelector('.left-door');
        const rightDoor = document.querySelector('.right-door');
        
        // 使用GSAP动画库
        gsap.to(leftDoor, {
            left: '-50%',
            duration: 1,
            ease: 'power2.out'
        });
        
        gsap.to(rightDoor, {
            right: '-50%',
            duration: 1,
            ease: 'power2.out'
        });
        
        // 5秒后关门
        setTimeout(() => {
            gsap.to(leftDoor, {
                left: '0',
                duration: 1,
                ease: 'power2.inOut'
            });
            
            gsap.to(rightDoor, {
                right: '0',
                duration: 1,
                ease: 'power2.inOut'
            });
        }, 5000);
    }

    // 事件监听
    verifyButton.addEventListener('click', () => {
        console.log('点击验证按钮');
        modal.style.display = 'flex';  // 使用flex而不是block
        startCamera();
    });

    closeButton.addEventListener('click', () => {
        console.log('点击关闭按钮');
        closeModal();
    });

    // 点击模态框外部关闭
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            console.log('点击模态框外部');
            closeModal();
        }
    });

    // 初始化时确保按钮可见和可交互
    verifyButton.style.visibility = 'visible';
    verifyButton.style.opacity = '1';
    verifyButton.style.pointerEvents = 'auto';
    
    // 初始化时确保成功提示框隐藏
    successModal.classList.remove('show');
}); 