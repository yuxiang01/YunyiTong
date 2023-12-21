import request from '@/utils/request'

// 查询费用列表
export function listExpense(query) {
  return request({
    url: '/os/expense/list',
    method: 'get',
    params: query
  })
}

// 查询费用详细
export function getExpense(expenseId) {
  return request({
    url: '/os/expense/' + expenseId,
    method: 'get'
  })
}

// 新增费用
export function addExpense(data) {
  return request({
    url: '/os/expense',
    method: 'post',
    data: data
  })
}

// 修改费用
export function updateExpense(data) {
  return request({
    url: '/os/expense',
    method: 'put',
    data: data
  })
}

// 删除费用
export function delExpense(expenseId) {
  return request({
    url: '/os/expense/' + expenseId,
    method: 'delete'
  })
}
