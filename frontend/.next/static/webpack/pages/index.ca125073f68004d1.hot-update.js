"use strict";
/*
 * ATTENTION: An "eval-source-map" devtool has been used.
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file with attached SourceMaps in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
self["webpackHotUpdate_N_E"]("pages/index",{

/***/ "./pages/index.jsx":
/*!*************************!*\
  !*** ./pages/index.jsx ***!
  \*************************/
/***/ (function(module, __webpack_exports__, __webpack_require__) {

eval(__webpack_require__.ts("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   __N_SSP: function() { return /* binding */ __N_SSP; },\n/* harmony export */   \"default\": function() { return /* binding */ HomePage; }\n/* harmony export */ });\n/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react/jsx-dev-runtime */ \"./node_modules/react/jsx-dev-runtime.js\");\n/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__);\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react */ \"./node_modules/react/index.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_1__);\n\nvar _s = $RefreshSig$();\n\nvar __N_SSP = true;\nfunction HomePage(param) {\n    let { listensData } = param;\n    _s();\n    const [likes, setLikes] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(0);\n    function handleClick() {\n        setLikes(likes + 1);\n    }\n    return /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"div\", {\n        children: [\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"ul\", {\n                children: listensData.genres.map((genre)=>/*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"li\", {\n                        children: gerne\n                    }, genre, false, {\n                        fileName: \"/Users/tilokowalski/Sites/statify/frontend/pages/index.jsx\",\n                        lineNumber: 58,\n                        columnNumber: 11\n                    }, this))\n            }, void 0, false, {\n                fileName: \"/Users/tilokowalski/Sites/statify/frontend/pages/index.jsx\",\n                lineNumber: 56,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"button\", {\n                onClick: handleClick,\n                children: [\n                    \"Like (\",\n                    likes,\n                    \")\"\n                ]\n            }, void 0, true, {\n                fileName: \"/Users/tilokowalski/Sites/statify/frontend/pages/index.jsx\",\n                lineNumber: 62,\n                columnNumber: 7\n            }, this)\n        ]\n    }, void 0, true, {\n        fileName: \"/Users/tilokowalski/Sites/statify/frontend/pages/index.jsx\",\n        lineNumber: 55,\n        columnNumber: 5\n    }, this);\n}\n_s(HomePage, \"m4sdoi624sGASZMfyLgbxubRP0c=\");\n_c = HomePage;\nvar _c;\n$RefreshReg$(_c, \"HomePage\");\n\n\n;\n    // Wrapped in an IIFE to avoid polluting the global scope\n    ;\n    (function () {\n        var _a, _b;\n        // Legacy CSS implementations will `eval` browser code in a Node.js context\n        // to extract CSS. For backwards compatibility, we need to check we're in a\n        // browser context before continuing.\n        if (typeof self !== 'undefined' &&\n            // AMP / No-JS mode does not inject these helpers:\n            '$RefreshHelpers$' in self) {\n            // @ts-ignore __webpack_module__ is global\n            var currentExports = module.exports;\n            // @ts-ignore __webpack_module__ is global\n            var prevSignature = (_b = (_a = module.hot.data) === null || _a === void 0 ? void 0 : _a.prevSignature) !== null && _b !== void 0 ? _b : null;\n            // This cannot happen in MainTemplate because the exports mismatch between\n            // templating and execution.\n            self.$RefreshHelpers$.registerExportsForReactRefresh(currentExports, module.id);\n            // A module can be accepted automatically based on its exports, e.g. when\n            // it is a Refresh Boundary.\n            if (self.$RefreshHelpers$.isReactRefreshBoundary(currentExports)) {\n                // Save the previous exports signature on update so we can compare the boundary\n                // signatures. We avoid saving exports themselves since it causes memory leaks (https://github.com/vercel/next.js/pull/53797)\n                module.hot.dispose(function (data) {\n                    data.prevSignature =\n                        self.$RefreshHelpers$.getRefreshBoundarySignature(currentExports);\n                });\n                // Unconditionally accept an update to this module, we'll check if it's\n                // still a Refresh Boundary later.\n                // @ts-ignore importMeta is replaced in the loader\n                module.hot.accept();\n                // This field is set when the previous version of this module was a\n                // Refresh Boundary, letting us know we need to check for invalidation or\n                // enqueue an update.\n                if (prevSignature !== null) {\n                    // A boundary can become ineligible if its exports are incompatible\n                    // with the previous exports.\n                    //\n                    // For example, if you add/remove/change exports, we'll want to\n                    // re-execute the importing modules, and force those components to\n                    // re-render. Similarly, if you convert a class component to a\n                    // function, we want to invalidate the boundary.\n                    if (self.$RefreshHelpers$.shouldInvalidateReactRefreshBoundary(prevSignature, self.$RefreshHelpers$.getRefreshBoundarySignature(currentExports))) {\n                        module.hot.invalidate();\n                    }\n                    else {\n                        self.$RefreshHelpers$.scheduleUpdate();\n                    }\n                }\n            }\n            else {\n                // Since we just executed the code for the module, it's possible that the\n                // new exports made it ineligible for being a boundary.\n                // We only care about the case when we were _previously_ a boundary,\n                // because we already accepted this update (accidental side effect).\n                var isNoLongerABoundary = prevSignature !== null;\n                if (isNoLongerABoundary) {\n                    module.hot.invalidate();\n                }\n            }\n        }\n    })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiLi9wYWdlcy9pbmRleC5qc3giLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7QUFBaUM7O0FBOENsQixTQUFTQyxTQUFTLEtBQWU7UUFBZixFQUFFQyxXQUFXLEVBQUUsR0FBZjs7SUFDL0IsTUFBTSxDQUFDQyxPQUFPQyxTQUFTLEdBQUdKLCtDQUFRQSxDQUFDO0lBRW5DLFNBQVNLO1FBQ1BELFNBQVNELFFBQVE7SUFDbkI7SUFFQSxxQkFDRSw4REFBQ0c7OzBCQUNDLDhEQUFDQzswQkFDRUwsWUFBWU0sTUFBTSxDQUFDQyxHQUFHLENBQUMsQ0FBQ0Msc0JBQ3ZCLDhEQUFDQztrQ0FBZ0JDO3VCQUFSRjs7Ozs7Ozs7OzswQkFJYiw4REFBQ0c7Z0JBQU9DLFNBQVNUOztvQkFBYTtvQkFBT0Y7b0JBQU07Ozs7Ozs7Ozs7Ozs7QUFHakQ7R0FsQndCRjtLQUFBQSIsInNvdXJjZXMiOlsid2VicGFjazovL19OX0UvLi9wYWdlcy9pbmRleC5qc3g/N2ZmZCJdLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyB1c2VTdGF0ZSB9IGZyb20gJ3JlYWN0JztcbmltcG9ydCB7IFN1cnJlYWwgfSBmcm9tICdzdXJyZWFsZGIuanMnO1xuXG5jb25zdCBkYiA9IG5ldyBTdXJyZWFsKCk7XG5cbmV4cG9ydCBhc3luYyBmdW5jdGlvbiBnZXRTZXJ2ZXJTaWRlUHJvcHMoY29udGV4dCkge1xuXG4gIGxldCBsaXN0ZW5zID0gbnVsbDtcblxuICB0cnkge1xuXHRcdC8vIENvbm5lY3QgdG8gdGhlIGRhdGFiYXNlXG5cdFx0YXdhaXQgZGIuY29ubmVjdCgnaHR0cDovLzEyNy4wLjAuMTo4MDAwL3JwYycsIHtcblx0XHRcdC8vIFNldCB0aGUgbmFtZXNwYWNlIGFuZCBkYXRhYmFzZSBmb3IgdGhlIGNvbm5lY3Rpb25cblx0XHRcdG5hbWVzcGFjZTogJ3Nwb3RpZnknLFxuXHRcdFx0ZGF0YWJhc2U6ICdzcG90aWZ5JyxcblxuXHRcdFx0Ly8gU2V0IHRoZSBhdXRoZW50aWNhdGlvbiBkZXRhaWxzIGZvciB0aGUgY29ubmVjdGlvblxuXHRcdFx0Ly8gYXV0aDoge1xuXHRcdFx0Ly8gXHRuYW1lc3BhY2U6ICd0ZXN0Jyxcblx0XHRcdC8vIFx0ZGF0YWJhc2U6ICd0ZXN0Jyxcblx0XHRcdC8vIFx0c2NvcGU6ICd1c2VyJyxcblx0XHRcdC8vIFx0dXNlcm5hbWU6ICdpbmZvQHN1cnJlYWxkYi5jb20nLFxuXHRcdFx0Ly8gXHRwYXNzd29yZDogJ215LXNlY3JldC1wYXNzd29yZCcsXG5cdFx0XHQvLyB9LFxuXHRcdH0pO1xuXG5cdFx0Ly8gU2VsZWN0IGFsbCBwZW9wbGUgcmVjb3Jkc1xuXHRcdC8vIGNvbnN0IHBlb3BsZSA9IGF3YWl0IGRiLnNlbGVjdCgncGVyc29uJyk7XG5cblx0XHRjb25zdCByZXN1bHQgPSBhd2FpdCBkYi5xdWVyeShcblx0XHRcdCdTRUxFQ1QgLT5saXN0ZW5zLT50cmFjay5hcnRpc3QuZ2VucmUgQVMgZ2VucmVzLCAtPmxpc3RlbnMucGxheWVkX2F0IEFTIHBsYXllZF9hdCBGUk9NIHVzZXI6eHl6OycsXG5cdFx0KTtcbiAgICBjb25zb2xlLmxvZyhyZXN1bHRbMF0pXG4gICAgbGlzdGVucyA9IHJlc3VsdFswXVswXTtcblxuXHR9IGNhdGNoIChlKSB7XG5cdFx0Y29uc29sZS5lcnJvcignRVJST1InLCBlKTtcblx0fVxuICBjb25zb2xlLmxvZyhsaXN0ZW5zKTtcbiAgcmV0dXJuIHtcbiAgICBwcm9wczoge1xuICAgICAgbGlzdGVuc0RhdGE6IGxpc3RlbnNcbiAgICB9LFxuICB9O1xufVxuXG5leHBvcnQgZGVmYXVsdCBmdW5jdGlvbiBIb21lUGFnZSh7IGxpc3RlbnNEYXRhIH0pIHtcbiAgY29uc3QgW2xpa2VzLCBzZXRMaWtlc10gPSB1c2VTdGF0ZSgwKTtcblxuICBmdW5jdGlvbiBoYW5kbGVDbGljaygpIHtcbiAgICBzZXRMaWtlcyhsaWtlcyArIDEpO1xuICB9XG5cbiAgcmV0dXJuIChcbiAgICA8ZGl2PlxuICAgICAgPHVsPlxuICAgICAgICB7bGlzdGVuc0RhdGEuZ2VucmVzLm1hcCgoZ2VucmUpID0+IChcbiAgICAgICAgICA8bGkga2V5PXtnZW5yZX0+e2dlcm5lfTwvbGk+XG4gICAgICAgICkpfVxuICAgICAgPC91bD5cblxuICAgICAgPGJ1dHRvbiBvbkNsaWNrPXtoYW5kbGVDbGlja30+TGlrZSAoe2xpa2VzfSk8L2J1dHRvbj5cbiAgICA8L2Rpdj5cbiAgKTtcbn0iXSwibmFtZXMiOlsidXNlU3RhdGUiLCJIb21lUGFnZSIsImxpc3RlbnNEYXRhIiwibGlrZXMiLCJzZXRMaWtlcyIsImhhbmRsZUNsaWNrIiwiZGl2IiwidWwiLCJnZW5yZXMiLCJtYXAiLCJnZW5yZSIsImxpIiwiZ2VybmUiLCJidXR0b24iLCJvbkNsaWNrIl0sInNvdXJjZVJvb3QiOiIifQ==\n//# sourceURL=webpack-internal:///./pages/index.jsx\n"));

/***/ })

});