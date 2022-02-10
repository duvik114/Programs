window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

ajaxFunction = function (data, success) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: data,
        success: success
    });
}

enterOrRegistrationForm = function (action, $form) {
    const $login = $form.find("input[name='login']").val();
    const $password = $form.find("input[name='password']").val();
    const $error = $form.find(".error");
    const data = {
        action: action,
        login: $login,
        password: $password
    }

    const success = defaultSuccess($error);

    ajaxFunction(data, success)
}

const defaultSuccess = function ($error) {
    return function (response) {
        if (response["error"]) {
            if ($error !== undefined) {
                $error.text(response["error"]);
            } else {
                notify(response["error"], 'error');
            }
        } else {
            location.href = response["redirect"];
        }
    }
}