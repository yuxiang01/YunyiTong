import request from '@/utils/request'

// 查询患者列表
export function listPatient(query) {
  return request({
    url: '/os/patient/list',
    method: 'get',
    params: query
  })
}

// 查询患者详细
export function getPatient(patientId) {
  return request({
    url: '/os/patient/' + patientId,
    method: 'get'
  })
}

// 新增患者
export function addPatient(data) {
  return request({
    url: '/os/patient',
    method: 'post',
    data: data
  })
}

// 修改患者
export function updatePatient(data) {
  return request({
    url: '/os/patient',
    method: 'put',
    data: data
  })
}

// 删除患者
export function delPatient(patientId) {
  return request({
    url: '/os/patient/' + patientId,
    method: 'delete'
  })
}
