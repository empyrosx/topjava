<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список приёмов пищи</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
</head>
<body>

<form action="<c:url value="/meals"/>" method="POST">
    <input type="hidden" name="mealId" value="${meal.id}"/>
    <table>
        <tr>
            <td>Дата:</td>
            <f:parseDate value="${meal.date}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
            <td><input type="text" name="date" value="<f:formatDate value="${parsedDate}" pattern="yyyy-MM-dd" />"/></td>
        </tr>
        <tr>
            <td>Время:</td>
            <f:parseDate value="${meal.time}" pattern="HH:mm" var="parsedTime" type="time" />
            <td><input type="text" name="time" value="<f:formatDate value="${parsedTime}" pattern="HH:mm" />"/></td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td><input type="text" name="description" value="${meal.description}"/></td>
        </tr>
        <tr>
            <td>Калории:</td>
            <td><input type="text" name="calories" value="${meal.calories}"/></td>
        </tr>
    </table>
    <table>
        <tr>
            <td>
                <button type="submit" name="action" value="save">Сохранить</button>
            </td>
            <td>
                <button type="submit" name="action" value="cancel">Отменить</button>
            </td>
        </tr>
    </table>
</form>

</body>
</html>