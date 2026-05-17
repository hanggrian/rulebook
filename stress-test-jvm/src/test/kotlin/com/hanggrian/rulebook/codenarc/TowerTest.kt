package com.hanggrian.rulebook.codenarc

import kotlin.test.Test

class TowerTest : AllRulesTest() {
    @Test
    fun io_seqera_tower_plugin_TowerFusionToken() =
        assertViolations(
            getCode("tower/TowerFusionToken.groovy"),
            violationOf(
                12,
                "import com.google.common.cache.Cache",
                "Remove blank line before directive 'com.google.common.cache.Cache'.",
            ),
            violationOf(
                12,
                "import com.google.common.cache.Cache",
                "Arrange directive 'com.google.common.cache.Cache' " +
                    "before 'java.util.function.Predicate'.",
            ),
            violationOf(
                66,
                "private final HttpClient httpClient = newDefaultHttpClient()",
                "Arrange member 'property' before 'static member'.",
            ),
            violationOf(
                110,
                "* @param config The Fusion configuration object",
                "End '@param' with a period.",
            ),
            violationOf(
                111,
                "* @return A map of environment variables",
                "End '@return' with a period.",
            ),
            violationOf(
                138,
                "* @return The signed JWT token",
                "Arrange tag '@return' before '@throws'.",
            ),
            violationOf(
                138,
                "* @return The signed JWT token",
                "End '@return' with a period.",
            ),
            violationOf(
                158,
                "return resp.signedToken",
                "Lift 'else' and add 'return' in 'if' block.",
            ),
            violationOf(
                175,
                "* @return The new HttpClient instance",
                "End '@return' with a period.",
            ),
            violationOf(
                175,
                "* @return The new HttpClient instance",
                "Add blank line before block tag group.",
            ),
            violationOf(
                178,
                "final builder = HttpClient.newBuilder()",
                "Put newline before '='.",
            ),
            violationOf(
                178,
                "final builder = HttpClient.newBuilder()",
                "Put newline before '.'.",
            ),
            violationOf(
                196,
                "* @return The new RetryPolicy instance",
                "End '@return' with a period.",
            ),
            violationOf(
                203,
                "final listener = new EventListener<ExecutionAttemptedEvent<HttpResponse<T>>>() {",
                "Put newline before '='.",
            ),
            violationOf(
                207,
                "if (event.lastResult != null)",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                209,
                "if (event.lastFailure != null)",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                228,
                "* @param req The HttpRequest to send",
                "End '@param' with a period.",
            ),
            violationOf(
                229,
                "* @return The HttpResponse received",
                "End '@return' with a period.",
            ),
            violationOf(
                231,
                "private <T> HttpResponse<String> " +
                    "safeHttpSend(HttpRequest req, RetryPolicy<T> policy) {",
                "Arrange member 'function' before 'static member'.",
            ),
            violationOf(
                238,
                "} as CheckedSupplier",
                "Put trailing comma.",
            ),
            violationOf(
                245,
                "* @param req The LicenseTokenRequest object",
                "End '@param' with a period.",
            ),
            violationOf(
                246,
                "* @return The resulting HttpRequest object",
                "End '@return' with a period.",
            ),
            violationOf(
                262,
                "* @param req The LicenseTokenRequest object",
                "End '@param' with a period.",
            ),
            violationOf(
                263,
                "* @return The resulting JSON string",
                "End '@return' with a period.",
            ),
            violationOf(
                272,
                "* @param json The String containing the JSON representation of the " +
                    "LicenseTokenResponse object",
                "End '@param' with a period.",
            ),
            violationOf(
                273,
                "* @return The resulting LicenseTokenResponse object",
                "End '@return' with a period.",
            ),
            violationOf(
                284,
                "* @param req The LicenseTokenRequest object",
                "End '@param' with a period.",
            ),
            violationOf(
                285,
                "* @return The LicenseTokenResponse object",
                "End '@return' with a period.",
            ),
            violationOf(
                292,
                "private GetLicenseTokenResponse sendRequest(GetLicenseTokenRequest req) throws " +
                    "AbortOperationException, " +
                    "UnauthorizedException, " +
                    "BadResponseException, " +
                    "IllegalStateException {",
                "Arrange member 'function' before 'static member'.",
            ),
            violationOf(
                299,
                "if( resp.statusCode() == 200 ) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                304,
                "if( resp.statusCode() == 401 ) {",
                "Replace equality with 'is()'.",
            ),
        )
}
