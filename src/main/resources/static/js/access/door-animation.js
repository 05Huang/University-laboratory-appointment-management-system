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
    const faceRecognitionModal = document.getElementById('faceRecognitionModal');
    const closeModalButton = document.getElementById('closeModal');
    const successModal = document.getElementById('successModal');
    const videoElement = document.getElementById('videoElement');
    const statusText = document.querySelector('.status-text');
    const leftDoor = document.querySelector('.left-door');
    const rightDoor = document.querySelector('.right-door');

    // 初始化变量
    let stream = null;
    let captureTimeout = null;
    let isVerifying = false;

    // 打开人脸识别弹窗
    function openFaceRecognitionModal() {
        if (isVerifying) return;
        console.log('打开人脸识别模态框');
        faceRecognitionModal.classList.add('show');
        startCamera();
    }

    // 关闭人脸识别弹窗
    function closeFaceRecognitionModal() {
        console.log('关闭人脸识别模态框');
        faceRecognitionModal.classList.remove('show');
        stopCamera();
    }

    // 重置验证状态
    function resetVerificationState() {
        if (captureTimeout) {
            clearTimeout(captureTimeout);
            captureTimeout = null;
        }
        isVerifying = false;
        statusText.textContent = '正在扫描...';
    }

    // 启动摄像头
    async function startCamera() {
        try {
            stream = await navigator.mediaDevices.getUserMedia({ video: true });
            videoElement.srcObject = stream;
            
            videoElement.onloadedmetadata = () => {
                videoElement.play();
                // 3秒后自动捕捉
                captureTimeout = setTimeout(() => {
                    if (!isVerifying) {
                        captureAndVerify();
                    }
                }, 3000);
            };
        } catch (err) {
            console.error('摄像头访问失败:', err);
            closeFaceRecognitionModal();
        }
    }

    // 停止摄像头
    function stopCamera() {
        if (stream) {
            stream.getTracks().forEach(track => track.stop());
            stream = null;
            videoElement.srcObject = null;
        }
    }

    // 捕捉图片并验证
    function captureAndVerify() {
        if (isVerifying) return;
        isVerifying = true;

        if (!videoElement.videoWidth || !videoElement.videoHeight) {
            console.error('视频未准备就绪');
            isVerifying = false;
            return;
        }

        console.log('开始人脸捕捉和验证');
        statusText.textContent = '验证中...';
        
        const canvas = document.createElement('canvas');
        canvas.width = videoElement.videoWidth;
        canvas.height = videoElement.videoHeight;
        const ctx = canvas.getContext('2d');
        ctx.drawImage(videoElement, 0, 0, canvas.width, canvas.height);
        
        // 模拟验证过程
        setTimeout(() => {
            closeFaceRecognitionModal();
            // 确保人脸识别模态框完全关闭后再显示成功提示
            setTimeout(() => {
                showSuccessModal();
            }, 300);
        }, 1000);
    }

    // 显示成功提示
    function showSuccessModal() {
        console.log('显示成功提示');
        // 隐藏验证按钮
        verifyButton.style.opacity = '0';
        verifyButton.style.visibility = 'hidden';
        
        // 显示成功提示框
        successModal.classList.add('show');
        
        // 3秒后隐藏成功提示并打开门
        setTimeout(() => {
            successModal.classList.remove('show');
            // 等待提示框消失的过渡动画完成后打开门
            setTimeout(() => {
                openDoors();
            }, 300);
        }, 3000);
    }

    // 打开门动画
    function openDoors() {
        console.log('打开门');
        leftDoor.classList.add('open');
        rightDoor.classList.add('open');
        
        setTimeout(() => {
            closeDoors();
        }, 5000);
    }

    // 关闭门动画
    function closeDoors() {
        console.log('关闭门');
        leftDoor.classList.remove('open');
        rightDoor.classList.remove('open');
        
        setTimeout(() => {
            verifyButton.style.opacity = '1';
            verifyButton.style.visibility = 'visible';
            resetVerificationState();
        }, 1500);
    }

    // 事件监听器
    verifyButton.addEventListener('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        if (!isVerifying) {
            openFaceRecognitionModal();
        }
    });

    closeModalButton.addEventListener('click', function(e) {
        e.preventDefault();
        closeFaceRecognitionModal();
        resetVerificationState();
    });

    // 点击模态框背景关闭
    faceRecognitionModal.addEventListener('click', (e) => {
        if (e.target === faceRecognitionModal) {
            closeFaceRecognitionModal();
            resetVerificationState();
        }
    });

    // ESC键关闭模态框
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && faceRecognitionModal.classList.contains('show')) {
            closeFaceRecognitionModal();
            resetVerificationState();
        }
    });

    // 初始化时确保按钮可见和可交互
    verifyButton.style.visibility = 'visible';
    verifyButton.style.opacity = '1';
    verifyButton.style.pointerEvents = 'auto';
    
    // 初始化时确保成功提示框隐藏
    successModal.classList.remove('show');
}); 