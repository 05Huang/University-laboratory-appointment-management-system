// Axios响应拦截器配置
(function() {
    // 需要自动刷新表格的请求URL模式
    const REFRESH_PATTERNS = [
        '/auth/laboratory',     // 实验室相关
        '/auth/instrument',     // 仪器相关
        '/api/laboratory',      // 实验室API
        '/api/instrument',      // 仪器API
        '/api/borrow'          // 借用相关
    ];

    // 检查URL是否匹配需要刷新的模式
    function shouldRefreshTable(url) {
        return REFRESH_PATTERNS.some(pattern => url.includes(pattern));
    }

    // 获取当前页面的bootstrap-table实例
    function getBootstrapTable() {
        // 常见的表格ID
        const tableIds = ['#table', '#dataTable', '#mainTable', '#table1', '#table2'];
        let tables = [];
        
        // 收集所有存在的表格
        for (let id of tableIds) {
            const $table = $(id);
            if ($table.length && $table.bootstrapTable) {
                tables.push($table);
            }
        }
        
        // 如果找到表格，刷新所有表格
        if (tables.length > 0) {
            return tables;
        }
        return null;
    }

    // 刷新表格
    function refreshTable() {
        const tables = getBootstrapTable();
        if (tables) {
            setTimeout(() => {
                tables.forEach($table => {
                    $table.bootstrapTable('refresh', {
                        silent: true
                    });
                });
            }, 100);
        }
    }

    // 添加响应拦截器
    axios.interceptors.response.use(
        function (response) {
            // 成功响应的处理
            if (response.config && shouldRefreshTable(response.config.url)) {
                // 如果是修改/删除/添加操作（非GET请求），则刷新表格
                if (response.config.method !== 'get') {
                    refreshTable();
                }
            }
            return response;
        },
        function (error) {
            // 错误响应的处理
            return Promise.reject(error);
        }
    );
})(); 