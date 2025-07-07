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
    const closeModal = document.getElementById('closeModal');
    const videoElement = document.getElementById('videoElement');
    const successModal = document.getElementById('successModal');
    const failureModal = document.getElementById('failureModal');
    const leftDoor = document.querySelector('.left-door');
    const rightDoor = document.querySelector('.right-door');

    let stream = null;
    let verificationInterval = null;

    // 打开摄像头
    async function startCamera() {
        try {
            stream = await navigator.mediaDevices.getUserMedia({ video: true });
            videoElement.srcObject = stream;
            
            // 等待视频加载完成后开始扫描
            videoElement.onloadedmetadata = () => {
                // 开始定时扫描
                startVerificationInterval();
            };
        } catch (err) {
            console.error('摄像头访问失败:', err);
            alert('无法访问摄像头，请确保已授予摄像头权限。');
        }
    }

    // 开始定时扫描
    function startVerificationInterval() {
        // 清除可能存在的旧定时器
        if (verificationInterval) {
            clearInterval(verificationInterval);
        }
        
        // 设置新的定时器
        verificationInterval = setInterval(async () => {
            if (videoElement.readyState === videoElement.HAVE_ENOUGH_DATA) {
                console.log('捕获视频帧并发送验证请求...');
                const imageBlob = await captureFrame();
                await verifyFace(imageBlob);
            }
        }, 3000);
    }

    // 停止定时扫描
    function stopVerificationInterval() {
        if (verificationInterval) {
            clearInterval(verificationInterval);
            verificationInterval = null;
        }
    }

    // 关闭摄像头
    function stopCamera() {
        stopVerificationInterval();
        if (stream) {
            stream.getTracks().forEach(track => track.stop());
            stream = null;
        }
    }

    // 捕获视频帧并转换为Blob
    function captureFrame() {
        const canvas = document.createElement('canvas');
        canvas.width = videoElement.videoWidth;
        canvas.height = videoElement.videoHeight;
        const ctx = canvas.getContext('2d');
        ctx.drawImage(videoElement, 0, 0);
        return new Promise(resolve => {
            canvas.toBlob(blob => resolve(blob), 'image/jpeg', 0.95);
        });
    }

    // 显示成功或失败提示
    function showResult(success) {
        faceRecognitionModal.style.display = 'none';
        stopCamera();
        
        if (success) {
            // 显示成功提示
            successModal.style.display = 'flex';
            successModal.style.opacity = '1';
            successModal.classList.add('show');
            
            // 播放开门动画
            setTimeout(() => {
                leftDoor.style.transform = 'translateX(-100%)';
                rightDoor.style.transform = 'translateX(100%)';
                
                // 3秒后关门
                setTimeout(() => {
                    leftDoor.style.transform = 'translateX(0)';
                    rightDoor.style.transform = 'translateX(0)';
                    successModal.style.opacity = '0';
                    successModal.classList.remove('show');
                    setTimeout(() => {
                        successModal.style.display = 'none';
                    }, 500);
                }, 3000);
            }, 1500);
        } else {
            // 显示失败提示
            failureModal.style.display = 'flex';
            failureModal.style.opacity = '1';
            failureModal.classList.add('show');
            
            // 2秒后隐藏失败提示
            setTimeout(() => {
                failureModal.style.opacity = '0';
                failureModal.classList.remove('show');
                setTimeout(() => {
                    failureModal.style.display = 'none';
                    // 重新打开摄像头，继续识别
                    faceRecognitionModal.style.display = 'flex';
                    startCamera();
                }, 500);
            }, 2000);
        }
    }

    // 发送人脸识别请求
    async function verifyFace(imageBlob) {
        const formData = new FormData();
        formData.append('file', imageBlob);

        try {
            console.log('发送人脸验证请求...');
            const response = await fetch('/api/face-recognition/verify', {
                method: 'POST',
                body: formData
            });
            
            const result = await response.json();
            console.log('收到验证结果:', result);
            
            if (result.code === 200) {
                showResult(result.data);
            } else {
                showResult(false);
                console.error('验证失败:', result.msg);
            }
        } catch (error) {
            showResult(false);
            console.error('请求失败:', error);
        }
    }

    // 事件监听器
    verifyButton.addEventListener('click', () => {
        console.log('打开人脸识别模态框...');
        faceRecognitionModal.style.display = 'flex';
        startCamera();
    });

    closeModal.addEventListener('click', () => {
        console.log('关闭人脸识别模态框...');
        faceRecognitionModal.style.display = 'none';
        stopCamera();
    });

    // 初始化时确保按钮可见和可交互
    verifyButton.style.visibility = 'visible';
    verifyButton.style.opacity = '1';
    verifyButton.style.pointerEvents = 'auto';
    
    // 初始化时确保成功提示框隐藏
    successModal.classList.remove('show');
}); 