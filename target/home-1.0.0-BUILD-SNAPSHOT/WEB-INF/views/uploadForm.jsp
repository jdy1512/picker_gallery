<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload Test Form</title>
</head>
<body>
	<form action="upload" method="post" enctype="multipart/form-data">
		<fieldset>
			<table>
				<tr>
					<th>파일</th>
					<td><input type="file" name="file" required="required"></td>
				</tr>
				<tr>
					<td><input type="submit" value="전송"></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>