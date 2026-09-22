import { En as ElementRef, In as Input, O as booleanAttribute, Ol as ɵɵdefineInjector, Rc as PLATFORM_ID, Wi as setClassMetadata, cl as inject, hc as DOCUMENT, no as ɵɵdefineDirective, qn as NgModule, ro as ɵɵdefineNgModule, wn as Directive } from "./core-DAuPG1Te.js";
import { u as isPlatformBrowser } from "./common-BCt_Kb9Z.js";
import { n as DomHandler } from "./primeng-dom-QnWno1CE.js";
//#region node_modules/primeng/fesm2022/primeng-autofocus.mjs
/**
* AutoFocus manages focus on focusable element on load.
* @group Components
*/
var AutoFocus = class AutoFocus {
	/**
	* When present, it specifies that the component should automatically get focus on load.
	* @group Props
	*/
	autofocus = false;
	focused = false;
	platformId = inject(PLATFORM_ID);
	document = inject(DOCUMENT);
	host = inject(ElementRef);
	ngAfterContentChecked() {
		if (this.autofocus === false) this.host.nativeElement.removeAttribute("autofocus");
		else this.host.nativeElement.setAttribute("autofocus", true);
		if (!this.focused) this.autoFocus();
	}
	ngAfterViewChecked() {
		if (!this.focused) this.autoFocus();
	}
	autoFocus() {
		if (isPlatformBrowser(this.platformId) && this.autofocus) setTimeout(() => {
			const focusableElements = DomHandler.getFocusableElements(this.host?.nativeElement);
			if (focusableElements.length === 0) this.host.nativeElement.focus();
			if (focusableElements.length > 0) focusableElements[0].focus();
			this.focused = true;
		});
	}
	static ɵfac = function AutoFocus_Factory(__ngFactoryType__) {
		return new (__ngFactoryType__ || AutoFocus)();
	};
	static ɵdir = /* @__PURE__ */ ɵɵdefineDirective({
		type: AutoFocus,
		selectors: [[
			"",
			"pAutoFocus",
			""
		]],
		hostAttrs: [1, "p-element"],
		inputs: { autofocus: [
			2,
			"autofocus",
			"autofocus",
			booleanAttribute
		] }
	});
};
(() => {
	(typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(AutoFocus, [{
		type: Directive,
		args: [{
			selector: "[pAutoFocus]",
			standalone: true,
			host: { class: "p-element" }
		}]
	}], null, { autofocus: [{
		type: Input,
		args: [{ transform: booleanAttribute }]
	}] });
})();
var AutoFocusModule = class AutoFocusModule {
	static ɵfac = function AutoFocusModule_Factory(__ngFactoryType__) {
		return new (__ngFactoryType__ || AutoFocusModule)();
	};
	static ɵmod = /* @__PURE__ */ ɵɵdefineNgModule({
		type: AutoFocusModule,
		imports: [AutoFocus],
		exports: [AutoFocus]
	});
	static ɵinj = /* @__PURE__ */ ɵɵdefineInjector({});
};
(() => {
	(typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(AutoFocusModule, [{
		type: NgModule,
		args: [{
			imports: [AutoFocus],
			exports: [AutoFocus]
		}]
	}], null, null);
})();
//#endregion
export { AutoFocusModule as n, AutoFocus as t };
