import { Dl as ɵɵdefineInjector, En as ElementRef, In as Input, Qn as Optional, Wi as setClassMetadata, Yo as ɵɵlistener, kn as HostListener, no as ɵɵdefineDirective, oo as ɵɵdirectiveInject, qn as NgModule, r as ChangeDetectorRef, ro as ɵɵdefineNgModule, wn as Directive, xa as ɵɵclassProp } from "./core-CK5_Em9Y.js";
import { L as NgModel } from "./forms-COsTWOWj.js";
import { C as CommonModule } from "./common-CVhezUSH.js";
import { f as PrimeNGConfig } from "./primeng-api-hFM0rYSV.js";
//#region node_modules/primeng/fesm2022/primeng-inputtext.mjs
/**
* InputText directive is an extension to standard input element with theming.
* @group Components
*/
var InputText = class InputText {
	el;
	ngModel;
	cd;
	config;
	/**
	* Specifies the input variant of the component.
	* @group Props
	*/
	variant = "outlined";
	filled;
	constructor(el, ngModel, cd, config) {
		this.el = el;
		this.ngModel = ngModel;
		this.cd = cd;
		this.config = config;
	}
	ngAfterViewInit() {
		this.updateFilledState();
		this.cd.detectChanges();
	}
	ngDoCheck() {
		this.updateFilledState();
	}
	onInput() {
		this.updateFilledState();
	}
	updateFilledState() {
		this.filled = this.el.nativeElement.value && this.el.nativeElement.value.length || this.ngModel && this.ngModel.model;
	}
	static ɵfac = function InputText_Factory(__ngFactoryType__) {
		return new (__ngFactoryType__ || InputText)(ɵɵdirectiveInject(ElementRef), ɵɵdirectiveInject(NgModel, 8), ɵɵdirectiveInject(ChangeDetectorRef), ɵɵdirectiveInject(PrimeNGConfig));
	};
	static ɵdir = /* @__PURE__ */ ɵɵdefineDirective({
		type: InputText,
		selectors: [[
			"",
			"pInputText",
			""
		]],
		hostAttrs: [
			1,
			"p-inputtext",
			"p-component",
			"p-element"
		],
		hostVars: 4,
		hostBindings: function InputText_HostBindings(rf, ctx) {
			if (rf & 1) ɵɵlistener("input", function InputText_input_HostBindingHandler($event) {
				return ctx.onInput($event);
			});
			if (rf & 2) ɵɵclassProp("p-filled", ctx.filled)("p-variant-filled", ctx.variant === "filled" || ctx.config.inputStyle() === "filled");
		},
		inputs: { variant: "variant" },
		standalone: false
	});
};
(() => {
	(typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(InputText, [{
		type: Directive,
		args: [{
			selector: "[pInputText]",
			host: {
				class: "p-inputtext p-component p-element",
				"[class.p-filled]": "filled",
				"[class.p-variant-filled]": "variant === \"filled\" || config.inputStyle() === \"filled\""
			}
		}]
	}], () => [
		{ type: ElementRef },
		{
			type: NgModel,
			decorators: [{ type: Optional }]
		},
		{ type: ChangeDetectorRef },
		{ type: PrimeNGConfig }
	], {
		variant: [{ type: Input }],
		onInput: [{
			type: HostListener,
			args: ["input", ["$event"]]
		}]
	});
})();
var InputTextModule = class InputTextModule {
	static ɵfac = function InputTextModule_Factory(__ngFactoryType__) {
		return new (__ngFactoryType__ || InputTextModule)();
	};
	static ɵmod = /* @__PURE__ */ ɵɵdefineNgModule({
		type: InputTextModule,
		declarations: [InputText],
		imports: [CommonModule],
		exports: [InputText]
	});
	static ɵinj = /* @__PURE__ */ ɵɵdefineInjector({ imports: [CommonModule] });
};
(() => {
	(typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(InputTextModule, [{
		type: NgModule,
		args: [{
			imports: [CommonModule],
			exports: [InputText],
			declarations: [InputText]
		}]
	}], null, null);
})();
//#endregion
export { InputText, InputTextModule };
