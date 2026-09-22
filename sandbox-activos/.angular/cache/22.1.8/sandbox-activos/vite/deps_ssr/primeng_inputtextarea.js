import { $n as Output, En as ElementRef, In as Input, O as booleanAttribute, Ol as ɵɵdefineInjector, Qn as Optional, Wi as setClassMetadata, Yo as ɵɵlistener, kn as HostListener, no as ɵɵdefineDirective, oo as ɵɵdirectiveInject, qn as NgModule, r as ChangeDetectorRef, ro as ɵɵdefineNgModule, wn as Directive, xa as ɵɵclassProp, xc as EventEmitter } from "./core-DAuPG1Te.js";
import { C as CommonModule } from "./common-BCt_Kb9Z.js";
import { f as PrimeNGConfig } from "./primeng-api-BkqxM_dX.js";
import { NgControl, NgModel } from "./@angular_forms.js";
//#region node_modules/primeng/fesm2022/primeng-inputtextarea.mjs
/**
* InputTextarea adds styling and autoResize functionality to standard textarea element.
* @group Components
*/
var InputTextarea = class InputTextarea {
	el;
	ngModel;
	control;
	cd;
	config;
	/**
	* When present, textarea size changes as being typed.
	* @group Props
	*/
	autoResize;
	/**
	* Specifies the input variant of the component.
	* @group Props
	*/
	variant = "outlined";
	/**
	* Callback to invoke on textarea resize.
	* @param {(Event | {})} event - Custom resize event.
	* @group Emits
	*/
	onResize = new EventEmitter();
	filled;
	cachedScrollHeight;
	ngModelSubscription;
	ngControlSubscription;
	constructor(el, ngModel, control, cd, config) {
		this.el = el;
		this.ngModel = ngModel;
		this.control = control;
		this.cd = cd;
		this.config = config;
	}
	ngOnInit() {
		if (this.ngModel) this.ngModelSubscription = this.ngModel.valueChanges.subscribe(() => {
			this.updateState();
		});
		if (this.control) this.ngControlSubscription = this.control.valueChanges.subscribe(() => {
			this.updateState();
		});
	}
	ngAfterViewInit() {
		if (this.autoResize) this.resize();
		this.updateFilledState();
		this.cd.detectChanges();
	}
	onInput(e) {
		this.updateState();
	}
	updateFilledState() {
		this.filled = this.el.nativeElement.value && this.el.nativeElement.value.length;
	}
	resize(event) {
		this.el.nativeElement.style.height = "auto";
		this.el.nativeElement.style.height = this.el.nativeElement.scrollHeight + "px";
		if (parseFloat(this.el.nativeElement.style.height) >= parseFloat(this.el.nativeElement.style.maxHeight)) {
			this.el.nativeElement.style.overflowY = "scroll";
			this.el.nativeElement.style.height = this.el.nativeElement.style.maxHeight;
		} else this.el.nativeElement.style.overflow = "hidden";
		this.onResize.emit(event || {});
	}
	updateState() {
		this.updateFilledState();
		if (this.autoResize) this.resize();
	}
	ngOnDestroy() {
		if (this.ngModelSubscription) this.ngModelSubscription.unsubscribe();
		if (this.ngControlSubscription) this.ngControlSubscription.unsubscribe();
	}
	static ɵfac = function InputTextarea_Factory(__ngFactoryType__) {
		return new (__ngFactoryType__ || InputTextarea)(ɵɵdirectiveInject(ElementRef), ɵɵdirectiveInject(NgModel, 8), ɵɵdirectiveInject(NgControl, 8), ɵɵdirectiveInject(ChangeDetectorRef), ɵɵdirectiveInject(PrimeNGConfig));
	};
	static ɵdir = /* @__PURE__ */ ɵɵdefineDirective({
		type: InputTextarea,
		selectors: [[
			"",
			"pInputTextarea",
			""
		]],
		hostAttrs: [
			1,
			"p-inputtextarea",
			"p-inputtext",
			"p-component",
			"p-element"
		],
		hostVars: 6,
		hostBindings: function InputTextarea_HostBindings(rf, ctx) {
			if (rf & 1) ɵɵlistener("input", function InputTextarea_input_HostBindingHandler($event) {
				return ctx.onInput($event);
			});
			if (rf & 2) ɵɵclassProp("p-filled", ctx.filled)("p-inputtextarea-resizable", ctx.autoResize)("p-variant-filled", ctx.variant === "filled" || ctx.config.inputStyle() === "filled");
		},
		inputs: {
			autoResize: [
				2,
				"autoResize",
				"autoResize",
				booleanAttribute
			],
			variant: "variant"
		},
		outputs: { onResize: "onResize" },
		standalone: false
	});
};
(() => {
	(typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(InputTextarea, [{
		type: Directive,
		args: [{
			selector: "[pInputTextarea]",
			host: {
				class: "p-inputtextarea p-inputtext p-component p-element",
				"[class.p-filled]": "filled",
				"[class.p-inputtextarea-resizable]": "autoResize",
				"[class.p-variant-filled]": "variant === \"filled\" || config.inputStyle() === \"filled\""
			}
		}]
	}], () => [
		{ type: ElementRef },
		{
			type: NgModel,
			decorators: [{ type: Optional }]
		},
		{
			type: NgControl,
			decorators: [{ type: Optional }]
		},
		{ type: ChangeDetectorRef },
		{ type: PrimeNGConfig }
	], {
		autoResize: [{
			type: Input,
			args: [{ transform: booleanAttribute }]
		}],
		variant: [{ type: Input }],
		onResize: [{ type: Output }],
		onInput: [{
			type: HostListener,
			args: ["input", ["$event"]]
		}]
	});
})();
var InputTextareaModule = class InputTextareaModule {
	static ɵfac = function InputTextareaModule_Factory(__ngFactoryType__) {
		return new (__ngFactoryType__ || InputTextareaModule)();
	};
	static ɵmod = /* @__PURE__ */ ɵɵdefineNgModule({
		type: InputTextareaModule,
		declarations: [InputTextarea],
		imports: [CommonModule],
		exports: [InputTextarea]
	});
	static ɵinj = /* @__PURE__ */ ɵɵdefineInjector({ imports: [CommonModule] });
};
(() => {
	(typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(InputTextareaModule, [{
		type: NgModule,
		args: [{
			imports: [CommonModule],
			exports: [InputTextarea],
			declarations: [InputTextarea]
		}]
	}], null, null);
})();
//#endregion
export { InputTextarea, InputTextareaModule };
