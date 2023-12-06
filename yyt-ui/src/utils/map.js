// 通过医生信息 => 科室-医生的级联信息
export function doctorsToMap(doctors) {
  return convertDataToHierarchy(doctors)
}

function convertDataToHierarchy(data) {
  const result = [];

  const groupedByDepartment = data.reduce((acc, doctor) => {
    if (!acc[doctor.deptName]) {
      acc[doctor.deptName] = [];
    }
    acc[doctor.deptName].push(doctor);
    return acc;
  }, {});


  for (const department in groupedByDepartment) {
    const departmentData = groupedByDepartment[department];
    const departmentItem = {
      label: department,
      value: departmentData[0].deptId,
      children: []
    };

    for (const doctor of departmentData) {
      departmentItem.children.push({
        label: doctor.name,
        value: doctor.doctorId
      });
    }

    result.push(departmentItem);
  }

  return result;
}
